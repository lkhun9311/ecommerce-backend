package com.ecommerce.api.domain.storeproduct.controller.model;

import com.ecommerce.db.storeproduct.enums.StoreProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreProductResponse {

    private Long id;

    private Long storeId;

    private String name;

    private BigDecimal amount;

    private StoreProductStatus status;

    private String thumbnailUrl;

    private int likeCount;
}
