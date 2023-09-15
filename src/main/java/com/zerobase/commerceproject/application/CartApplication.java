package com.zerobase.commerceproject.application;


import com.zerobase.commerceproject.domain.emtity.Product;
import com.zerobase.commerceproject.domain.emtity.ProductItem;
import com.zerobase.commerceproject.domain.product.AddProductCartForm;
import com.zerobase.commerceproject.domain.redis.Cart;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.exception.ErrorCode;
import com.zerobase.commerceproject.service.customer.CartService;
import com.zerobase.commerceproject.service.product.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zerobase.commerceproject.exception.ErrorCode.ITEM_COUNT_NOT_ENOUGH;

@Service
@RequiredArgsConstructor
public class CartApplication {
    private final ProductSearchService productSearchService;
    private final CartService cartService;

    public Cart addCart(Long customerId, AddProductCartForm form){
        Product product = productSearchService.getByProductId(form.getId());
        if(product == null){
            throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
        }
        Cart cart = cartService.getCart(customerId);

        //카트가 없거나 추가 불가능하면
        if(cart != null && !addAble(cart, product, form)){
            throw new CustomException(ITEM_COUNT_NOT_ENOUGH);
        }

        return cartService.addCart(customerId,form);
    }

    private boolean addAble(Cart cart, Product product, AddProductCartForm form){
        Cart.Product cartProduct = cart.getProducts().stream().filter(p -> p.getId().equals(form.getId()))
                .findFirst()
                .orElse(Cart.Product.builder().id(product.getId()).items(Collections.emptyList()).build());

        //카트 아이템 카운트
        Map<Long, Integer> cartItemCountMap = cartProduct.getItems().stream()
                .collect(Collectors.toMap(Cart.ProductItem::getId, Cart.ProductItem::getCount));
        //현재 아이템 카운트
        Map<Long, Integer> currentItemCountMap = product.getProductItems().stream()
                .collect(Collectors.toMap(ProductItem::getId, ProductItem::getCount));
        //상품 수량 초과
        return form.getItems().stream().noneMatch(
                formItem -> {
                    Integer cartCount = cartItemCountMap.get(formItem.getId());
                    if(cartCount == null){
                        cartCount = 0;
                    }
                    Integer currentCount = currentItemCountMap.get(formItem.getId());
                    return formItem.getCount() + cartCount > currentCount;
                });
    }

    public Cart getCart(Long customerId){
        Cart cart = refreshCart(cartService.getCart(customerId));
        cartService.putCart(cart.getCustomerId(),cart);

        Cart returnCart = new Cart();

        returnCart.setCustomerId(customerId);
        returnCart.setProducts(cart.getProducts());
        returnCart.setMessages(cart.getMessages());

        cart.setMessages(new ArrayList<>());
        cartService.putCart(customerId,cart);

        return returnCart;
    }

    protected Cart refreshCart(Cart cart){

        Map<Long, Product> productMap = productSearchService.getListByProductIds(
                        cart.getProducts().stream().map(Cart.Product::getId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Product::getId,product -> product));

        for (int i=0;i<cart.getProducts().size();i++){

            Cart.Product cartProduct = cart.getProducts().get(i);
            Product p = productMap.get(cartProduct.getId());

            if(p == null){
                cart.getProducts().remove(cartProduct);
                i--;
                cart.addMessage(cartProduct.getName()+" 상품이 삭제되었습니다.");
                continue;
            }

            Map<Long, ProductItem> productItemMap = p.getProductItems().stream()
                    .collect(Collectors.toMap(ProductItem::getId,productItem -> productItem));


            List<String> tmpMessage = new ArrayList<>();
            for (int j=0;j<cartProduct.getItems().size();j++){

                Cart.ProductItem cartProductItem = cartProduct.getItems().get(j);
                ProductItem pi = productItemMap.get(cartProductItem.getId());

                if(pi == null){
                    cartProduct.getItems().remove(cartProductItem);
                    j--;
                    tmpMessage.add(cartProductItem.getName()+" 옵션이 삭제되었습니다.");
                    continue;
                }

                boolean isPriceChanged = false,isCountNotEnough=false;
                //가격이랑 카운트 찾기
                if(!cartProductItem.getPrice().equals(pi.getPrice())){
                    isPriceChanged=true;
                    cartProductItem.setPrice(pi.getPrice());
                }

                if(cartProductItem.getCount() > pi.getCount()){
                    isCountNotEnough=true;
                    cartProductItem.setCount(pi.getCount());
                }

                if(isCountNotEnough && isPriceChanged){
                    tmpMessage.add(cartProductItem.getName()+" 가격과 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
                } else if (isPriceChanged) {
                    tmpMessage.add(cartProductItem.getName()+" 가격이 변동되었습니다.");
                } else if (isCountNotEnough) {
                    tmpMessage.add(cartProductItem.getName()+" 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
                }
            }

            if(cartProduct.getItems().size() == 0){
                cart.getProducts().remove(cartProduct);
                i--;
                cart.addMessage(cartProduct.getName()+" 상품의 옵션이 모두 없어져 구매가 불가능합니다.");

            }else if(tmpMessage.size() > 0){
                StringBuilder builder = new StringBuilder();
                builder.append(cartProduct.getName() +" 상품의 변동 사항 : ");
                for (String message : tmpMessage){
                    builder.append(message);
                    builder.append(", ");
                }
                cart.addMessage(builder.toString());
            }
        }

        return cart;
    }

    public Cart updateCart(Long customerId, Cart cart){
        cartService.putCart(customerId,cart);
        return getCart(customerId);
    }

    public void clearCart(Long customerId){
        cartService.putCart(customerId,null);
    }

}
