package com.zerobase.commerceproject.service.product;

import com.zerobase.commerceproject.domain.emtity.Product;
import com.zerobase.commerceproject.domain.emtity.ProductItem;
import com.zerobase.commerceproject.domain.product.AddProductItemForm;
import com.zerobase.commerceproject.domain.product.UpdateDiscountForm;
import com.zerobase.commerceproject.domain.product.UpdateProductItemForm;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.repository.ProductItemRepository;
import com.zerobase.commerceproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.commerceproject.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ProductItemService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Transactional
    public ProductItem getProductItem(Long id){
        return productItemRepository.getReferenceById(id);
    }

    @Transactional
    public Product addProductItem(Long sellerId, AddProductItemForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
        if(product.getProductItems().stream()
                .anyMatch(item -> item.getName().equals(form.getName()))){
            throw new CustomException(SAME_ITEM_NAME);
        }

        ProductItem productItem = ProductItem.of(sellerId,form);
        product.getProductItems().add(productItem);
        return product;
    }

    @Transactional
    public ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form){
        ProductItem productItem = productItemRepository.findById(form.getId())
                .filter(pi -> pi.getSellerId().equals(sellerId)).orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));
        productItem.setName(form.getName());
        productItem.setCount(form.getCount());
        productItem.setPrice(form.getPrice());
        return productItem;
    }

    @Transactional
    public ProductItem updateDiscount(Long sellerId, UpdateDiscountForm form){
        ProductItem productItem = productItemRepository.findById(form.getId())
                .filter(pi -> pi.getSellerId().equals(sellerId)).orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));
        productItem.setDiscount(form.getDiscount());
        return productItem;
    }

    @Transactional
    public void deleteProductItem(Long sellerId, Long productItemId){
        ProductItem productItem = productItemRepository.findById(productItemId)
                .filter(pi -> pi.getSellerId().equals(sellerId)).orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));
        productItemRepository.delete(productItem);
    }

}
