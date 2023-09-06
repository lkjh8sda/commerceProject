package com.zerobase.commerceproject.service.customer;

import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.repository.CustomerRepository;
import com.zerobase.commerceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    public Optional<User> findByIdAndEmail(Long id, String email){
        return userRepository.findById(id)
                .stream().filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getUserType().toString().equals("ALL") || user.getUserType().toString().equals("CUSTOMER"))
                .findFirst();
    }

}
