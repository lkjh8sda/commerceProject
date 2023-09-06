package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.application.SignInApplication;
import com.zerobase.commerceproject.component.MailComponent;
import com.zerobase.commerceproject.domain.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignInController {
    private final SignInApplication signInApplication;
    @PostMapping(value = "/signin")
    public ResponseEntity<String> signIn(@RequestBody SignInForm form){
        return ResponseEntity.ok(signInApplication.loginToken(form));
    }


}
