package com.zerobase.commerceproject.repository;

import com.zerobase.commerceproject.domain.emtity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchByName(String name);
}
