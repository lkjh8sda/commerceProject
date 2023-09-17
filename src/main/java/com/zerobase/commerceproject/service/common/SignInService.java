package com.zerobase.commerceproject.service.common;

import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zerobase.commerceproject.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final UserRepository userRepository;

    public Optional<User> findValidUser(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        String hashedPassword = user.getPassword();
        if(!BCrypt.checkpw(password,hashedPassword)){
            throw new CustomException(LOGIN_CHECK_FAIL);
        }
        return userRepository.findByEmailAndPasswordAndAndVerifyIsTrue(email,hashedPassword);
    }
}
