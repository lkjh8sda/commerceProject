package com.zerobase.commerceproject.domain.product;

import com.zerobase.commerceproject.domain.emtity.ProductItem;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemDTO {
    private Long id;
    private String name;
    private Integer price;
    private Integer count;

    public static ProductItemDTO from(ProductItem item){
        return ProductItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .count(item.getCount())
                .build();
    }
}
