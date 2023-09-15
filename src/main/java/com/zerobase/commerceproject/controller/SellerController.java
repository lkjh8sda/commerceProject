package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.user.UserVo;
import com.zerobase.commerceproject.domain.user.UserDTO;
import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.service.seller.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.commerceproject.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequestMapping("/seller")
@AllArgsConstructor
public class SellerController {
    private final JwtAuthenticationProvider provider;
    private final SellerService sellerService;
    @GetMapping("/getInfo")
    public ResponseEntity<UserDTO> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token){
        UserVo userVo = provider.getUserVo(token);
        User user = sellerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return ResponseEntity.ok(UserDTO.from(user));
    }

}
