package com.zerobase.commerceproject.repository;

import com.zerobase.commerceproject.domain.emtity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<User, Long> {
}
