package com.zerobase.commerceproject.domain.product;

import com.zerobase.commerceproject.domain.emtity.Product;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private List<ProductItemDTO> items;

    public static ProductDTO from(Product product){
        List<ProductItemDTO> items = product.getProductItems()
                .stream().map(ProductItemDTO::from).collect(Collectors.toList());

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .items(items)
                .build();
    }
    //하위 데이터 없이 리턴
    public static ProductDTO withoutItemsFrom(Product product){

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }
}
