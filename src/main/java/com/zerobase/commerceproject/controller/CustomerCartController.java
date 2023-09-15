package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.application.CartApplication;
import com.zerobase.commerceproject.application.OrderApplication;
import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.product.AddProductCartForm;
import com.zerobase.commerceproject.domain.redis.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {

    private final JwtAuthenticationProvider provider;
    private final CartApplication cartApplication;
    private final OrderApplication orderApplication;

    @PostMapping
    public ResponseEntity<Cart> addCart(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody AddProductCartForm form){
        return ResponseEntity.ok(cartApplication.addCart(provider.getUserVo(token).getId(),form));
    }

    @GetMapping
    public ResponseEntity<Cart> showCart(
            @RequestHeader(name = "X-AUTH-TOKEN") String token){
        return ResponseEntity.ok(cartApplication.getCart(provider.getUserVo(token).getId()));

    }

    @PutMapping
    public ResponseEntity<Cart> updateCart(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody Cart cart){
        return ResponseEntity.ok(cartApplication.updateCart(provider.getUserVo(token).getId(),cart));
    }

    @PostMapping("/order")
    public ResponseEntity<Cart> order(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody Cart cart){
        orderApplication.order(token,cart);
        return ResponseEntity.ok().build();
    }

}
