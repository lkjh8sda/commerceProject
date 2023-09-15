package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.product.ProductDTO;
import com.zerobase.commerceproject.service.product.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search/product")
public class SearchController {
    private final ProductSearchService productSearchService;
    private final JwtAuthenticationProvider provider;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> searchByName(@RequestParam String name){
        return ResponseEntity.ok(
                productSearchService.searchByName(name).stream()
                        .map(ProductDTO::withoutItemsFrom).collect(Collectors.toList())
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<ProductDTO> getDetail (@RequestParam Long productId){
        return ResponseEntity.ok(
                ProductDTO.from(productSearchService.getByProductId(productId))
        );
    }
}
