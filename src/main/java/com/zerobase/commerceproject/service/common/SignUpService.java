package com.zerobase.commerceproject.service.common;

import com.zerobase.commerceproject.domain.SignUpForm;
import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

import static com.zerobase.commerceproject.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;

    // 이메일 존재 여부
    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Transactional
    public String signUp(SignUpForm form) {
        String encPassword = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
        form.setPassword(encPassword);
        userRepository.save(User.from(form));
        return "가입 신청이 완료되었습니다. 이메일 인증을 완료해주세요";
    }

    public Optional<User> findByVerificationCode(String uuid){
        return userRepository.findByVerificationCode(uuid);
    }

    @Transactional
    public void verifyEmail(String uuid){
        User user = this.findByVerificationCode(uuid)
                .orElseThrow(() -> new CustomException(NOT_REGISTER_USER));

        //이미 증명완료
        if(user.isVerify()){
            throw new CustomException(ALREADY_VERIFY);
        }
        //인증코드 다름
        if(!user.getVerificationCode().equals(uuid)){
            throw new CustomException(WRONG_VERIFICATION);
        }

        user.setVerify(true);
    }
}
