package com.zerobase.commerceproject.application;

import com.zerobase.commerceproject.client.UserClient;
import com.zerobase.commerceproject.component.MailComponent;
import com.zerobase.commerceproject.domain.ChangeBalanceForm;
import com.zerobase.commerceproject.domain.emtity.OrderHistory;
import com.zerobase.commerceproject.domain.emtity.ProductItem;
import com.zerobase.commerceproject.domain.product.AddProductCartForm;
import com.zerobase.commerceproject.domain.redis.Cart;
import com.zerobase.commerceproject.domain.user.UserDTO;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.exception.ErrorCode;
import com.zerobase.commerceproject.repository.CustomerRepository;
import com.zerobase.commerceproject.repository.OrderHistoryRepository;
import com.zerobase.commerceproject.service.product.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OrderApplication {

    private final CartApplication cartApplication;
    private final UserClient userClient;
    private final ProductItemService productItemService;
    private final MailComponent mailComponent;
    private final OrderHistoryRepository orderHistoryRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void order(String token, Cart cart){
        Cart orderCart = cartApplication.refreshCart(cart);
        if(orderCart.getMessages().size() > 0){
            throw new CustomException(ErrorCode.ORDER_FAIL_CHECK_CART);
        }
        UserDTO userDTO = userClient.getCustomerInfo(token).getBody();

        int totalPrice = getTotalPrice(cart);
        int discount = productItemService.getProductItem(userDTO.getId()).getDiscount();
        int finalPrice = totalPrice - discount;
        if(userDTO.getBalance() < finalPrice){
            throw new CustomException(ErrorCode.ORDER_FAIL_NO_MONEY);
        }

        userClient.changeBalance(token,
                ChangeBalanceForm.builder()
                        .from("USER")
                        .message("Order")
                        .money(-finalPrice)
                        .build());
        StringBuilder orderName = new StringBuilder();
        for (Cart.Product product : orderCart.getProducts()){
            orderName.append(product.getName()+",");
            for (Cart.ProductItem cartItem : product.getItems()){
                ProductItem productItem = productItemService.getProductItem(cartItem.getId());
                productItem.setCount(productItem.getCount() - cartItem.getCount());
            }
        }

        //결제완료 메일발송
        String email = userDTO.getEmail();
        String subject = "주문이 완료되었습니다";
        String text = "<p>주문이 완료되었습니다.</p>";
               // +"<div><a href='http://localhost:8082/signup/verify?id="+uuid+"'>가입 완료 </a></div>";

        mailComponent.sendMail(email,subject,text);

        //주문 히스토리
        OrderHistory orderHistory = OrderHistory.builder()
                .email(userDTO.getEmail())
                .name(orderName.toString())
                .user(customerRepository.findById(userDTO.getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
                .build();
        orderHistoryRepository.save(orderHistory);

    }

    private Integer getTotalPrice(Cart cart){
        return cart.getProducts().stream().flatMapToInt(
                product -> product.getItems().stream().flatMapToInt(
                        productItem -> IntStream.of(productItem.getPrice() * productItem.getCount())
                )
        ).sum();
    }

}
