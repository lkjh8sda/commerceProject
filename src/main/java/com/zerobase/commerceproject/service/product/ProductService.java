package com.zerobase.commerceproject.service.product;

import com.zerobase.commerceproject.domain.emtity.Product;
import com.zerobase.commerceproject.domain.emtity.ProductItem;
import com.zerobase.commerceproject.domain.product.AddProductForm;
import com.zerobase.commerceproject.domain.product.UpdateProductForm;
import com.zerobase.commerceproject.domain.product.UpdateProductItemForm;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.commerceproject.exception.ErrorCode.NOT_FOUND_ITEM;
import static com.zerobase.commerceproject.exception.ErrorCode.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product addProduct(Long sellerId, AddProductForm addProductForm){
        return productRepository.save(Product.of(sellerId,addProductForm));
    }

    @Transactional
    public Product updateProduct(Long sellerId, UpdateProductForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId,form.getId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

        product.setName(form.getName());
        product.setDescription(form.getDescription());

        for (UpdateProductItemForm itemForm : form.getItems()){
            ProductItem item = product.getProductItems().stream()
                    .filter(pi -> pi.getId().equals(itemForm.getId()))
                    .findFirst().orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));
            item.setName(itemForm.getName());
            item.setPrice(itemForm.getPrice());
            item.setCount(itemForm.getCount());
        }
        return product;
    }

    @Transactional
    public void deleteProduct(Long sellerId, Long productId){
        Product product = productRepository.findBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
        productRepository.delete(product);
    }

}
