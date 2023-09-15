package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.product.*;
import com.zerobase.commerceproject.service.product.ProductItemService;
import com.zerobase.commerceproject.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {
    private final ProductService productService;
    private final ProductItemService productItemService;
    private final JwtAuthenticationProvider provider;


    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody AddProductForm form){

        return ResponseEntity.ok(ProductDTO.from(productService.addProduct(provider.getUserVo(token).getId() , form)));
    }

    @PostMapping("/item")
    public ResponseEntity<ProductDTO> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                     @RequestBody AddProductItemForm form){

        return ResponseEntity.ok(ProductDTO.from(productItemService.addProductItem(provider.getUserVo(token).getId() , form)));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                    @RequestBody UpdateProductForm form){

        return ResponseEntity.ok(ProductDTO.from(productService.updateProduct(provider.getUserVo(token).getId() , form)));
    }

    @PutMapping("/item")
    public ResponseEntity<ProductItemDTO> updateProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                            @RequestBody UpdateProductItemForm form){

        return ResponseEntity.ok(ProductItemDTO.from(productItemService.updateProductItem(provider.getUserVo(token).getId() , form)));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @RequestParam Long id){

        productService.deleteProduct(provider.getUserVo(token).getId() , id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item")
    public ResponseEntity<Void> deleteProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                  @RequestParam Long id){
        productItemService.deleteProductItem(provider.getUserVo(token).getId() , id);
        return ResponseEntity.ok().build();
    }

}
