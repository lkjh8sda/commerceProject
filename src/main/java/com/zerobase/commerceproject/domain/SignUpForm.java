package com.zerobase.commerceproject.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SignUpForm {

    private String password;
    private String name;
    private String email;
    private String phone;
    private String verificationCode;
    private String userType;
}
