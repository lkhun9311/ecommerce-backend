package com.ecommerce.product.entity;

import com.ecommerce.common.model.enums.StoreProductStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "store_product")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreProductEntity {
    @Id
    @Column(length = 100, nullable = false)
    private String storeProductId;

    @Column(length = 100, nullable = false)
    private String storeId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreProductStatus status;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    private int likeCount;

    @Column(length = 50, nullable = false)
    private String color;
}
