package com.zerobase.commerceproject.service.seller;

import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final UserRepository userRepository;

    public Optional<User> findByIdAndEmail(Long id, String email){
        return userRepository.findById(id)
                .stream().filter(user -> user.getEmail().equals(email) && user.getUserType().toString().equals("ALL") || user.getUserType().toString().equals("SELLER"))
                .findFirst();
    }
}
