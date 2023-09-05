package com.zerobase.commerceproject.application;

import com.zerobase.commerceproject.component.MailComponent;
import com.zerobase.commerceproject.domain.SignUpForm;
import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.exception.CustomerException;
import com.zerobase.commerceproject.exception.ErrorCode;
import com.zerobase.commerceproject.service.common.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {

    private final SignUpService signUpService;
    private final MailComponent mailComponent;

    /*
    * 회원 가입
    * */
    public String signUp(SignUpForm form){

        if(signUpService.isEmailExist(form.getEmail())){
            throw new CustomerException(ErrorCode.ALREADY_REGISTER_USE);
        }

        String uuid = UUID.randomUUID().toString();
        form.setVerificationCode(uuid);

        signUpService.signUp(form);
        LocalDateTime now = LocalDateTime.now();

        //이메일 인증
        String email = form.getEmail();
        String subject = "회원가입을 축하드립니다.";
        String text = "<p>회원가입을 축하드립니다.</p><p>아래 링크를 클릭해 가입을 완료하세요.</p>"
                +"<div><a href='http://localhost:8082/signup/verify?id="+uuid+"'>가입 완료 </a></div>";

        mailComponent.sendMail(email,subject,text);

        return "회원 가입이 신청되었습니다. 이메일을 통해 인증을 완료해주세요";
    }

    public Optional<User> findByVerificationCode(String uuid){
        return signUpService.findByVerificationCode(uuid);
    }

    public void verifyEmail(String uuid){
        signUpService.verifyEmail(uuid);
    }

}
