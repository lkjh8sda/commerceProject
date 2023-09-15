package com.zerobase.commerceproject.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.commerceproject.domain.emtity.Product;
import com.zerobase.commerceproject.domain.emtity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchByName(String name) {
        String search = "%"+name+"%";

        QProduct product = QProduct.product;
        return queryFactory.selectFrom(product)
                .where(product.name.like(search))
                .fetch();
    }
}
