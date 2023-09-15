package com.zerobase.commerceproject.service.customer;

import com.zerobase.commerceproject.client.RedisClient;
import com.zerobase.commerceproject.domain.product.AddProductCartForm;
import com.zerobase.commerceproject.domain.redis.Cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final RedisClient redisClient;

    public Cart putCart(Long customerId,Cart cart){
        redisClient.put(customerId, cart);
        return cart;
    }

    public Cart getCart(Long customerId){
        Cart cart = redisClient.get(customerId, Cart.class);
        return cart != null ? cart:new Cart();
    }

    public Cart addCart(Long customerId, AddProductCartForm form){

        Cart cart = redisClient.get(customerId, Cart.class);

        if(cart == null){
            cart = new Cart();
            cart.setCustomerId(customerId);
        }

        Optional<Cart.Product> productOptional = cart.getProducts().stream()
                .filter(product1 -> product1.getId().equals(form.getId()))
                .findFirst();

        if(productOptional.isPresent()){
            //카트가 존재한다면
            Cart.Product redisProduct = productOptional.get();
            List<Cart.ProductItem> items = form.getItems().stream().map(Cart.ProductItem::from).collect(Collectors.toList());

            Map<Long,Cart.ProductItem> redisItemMap = redisProduct.getItems().stream()
                    .collect(Collectors.toMap(it -> it.getId(), it -> it));
            //카트의 상품이름과 추가하려는 상품이름이 다르다면
            if(!redisProduct.getName().equals(form.getName())){
                cart.addMessage(redisProduct.getName()+"의 정보가 변경되었습니다. 확인 부탁드립니다.");
            }

            for(Cart.ProductItem item : items){
                Cart.ProductItem redisItem = redisItemMap.get(item.getId());

                if(redisItem == null){
                    //레디스에 아이템이 없으면 추가
                    redisProduct.getItems().add(item);
                }else {
                    //상품 가격이 변경되었을때
                    if(!redisItem.getPrice().equals(item.getPrice())){
                        cart.addMessage(redisProduct.getName()+item.getName()+" 의 가격이 변경되었습니다. 확인 부탁드립니다.");
                    }
                    //상품 카운트 증가
                    redisItem.setCount(redisItem.getCount() + item.getCount());
                }
            }

        }else{
            //카트가 없다면
            Cart.Product product = Cart.Product.from(form);
            cart.getProducts().add(product);
        }

        redisClient.put(customerId, cart);
        return cart;
    }
}
