package com.zerobase.commerceproject.service.common;

import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final UserRepository userRepository;

    public Optional<User> findValidUser(String email, String password){
        return userRepository.findByEmailAndPasswordAndAndVerifyIsTrue(email,password);
    }
}
