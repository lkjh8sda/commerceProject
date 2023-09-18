package com.zerobase.commerceproject.domain.emtity;

import com.zerobase.commerceproject.domain.product.AddProductItemForm;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class ProductItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;
    private String name;

    @Audited
    private Integer price;

    @ColumnDefault("0")
    @Audited
    private Integer discount;
    @Audited
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public static ProductItem of(Long sellerId, AddProductItemForm form){
        return ProductItem.builder()
                .sellerId(sellerId)
                .name(form.getName())
                .price(form.getPrice())
                .discount(form.getDiscount())
                .count(form.getCount())
                .build();
    }

}
