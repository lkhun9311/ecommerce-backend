package com.ecommerce.product.entity;

import com.ecommerce.common.model.enums.StoreProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "store_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreProductEntity {
    @Id
    @Column(nullable = false)
    private String storeProductId;

    @Column(nullable = false)
    private Long storeId;

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
