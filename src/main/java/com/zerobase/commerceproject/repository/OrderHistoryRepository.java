package com.zerobase.commerceproject.repository;

import com.zerobase.commerceproject.domain.emtity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Long> {
}
