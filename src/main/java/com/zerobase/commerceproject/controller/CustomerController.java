package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.UserVo;
import com.zerobase.commerceproject.domain.UserDTO;
import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.exception.CustomerException;
import com.zerobase.commerceproject.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.commerceproject.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequestMapping(value = "/customer")
@AllArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    @GetMapping("/getInfo")
    public ResponseEntity<UserDTO> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token){
        UserVo userVo = provider.getUserVo(token);
        User user = customerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
                .orElseThrow(() -> new CustomerException(NOT_FOUND_USER));
        return ResponseEntity.ok(UserDTO.from(user));
    }
}
