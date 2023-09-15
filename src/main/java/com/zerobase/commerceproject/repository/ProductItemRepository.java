package com.zerobase.commerceproject.repository;

import com.zerobase.commerceproject.domain.emtity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

}
