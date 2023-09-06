package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.application.SignUpApplication;
import com.zerobase.commerceproject.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpApplication signUpApplication;
    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody SignUpForm signUpForm){
        return ResponseEntity.ok(signUpApplication.signUp(signUpForm));
    }

    @GetMapping(value = "/verify")
    public ResponseEntity signUpVerify(@RequestParam(value = "id")String uuid){
        signUpApplication.verifyEmail(uuid);

        return ResponseEntity.ok("가입이 완료되었습니다.");
    }


}
