package com.zerobase.commerceproject.application;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.SignInForm;
import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.service.common.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.commerceproject.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final SignInService signInService;
    private final JwtAuthenticationProvider provider;

    public String loginToken(SignInForm form){
        User user = signInService.findValidUser(form.getEmail(),form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        return provider.createToken(user.getEmail(), user.getId(), user.getUserType());
    }

}
