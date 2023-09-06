package com.zerobase.commerceproject.repository;

import com.zerobase.commerceproject.domain.emtity.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory,Long> {
    Optional<BalanceHistory> findFirstByUser_IdOrderByIdDesc(@RequestParam("user_id") Long userId);
}
