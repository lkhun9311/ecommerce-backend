package com.ecommerce.product.domain.storeproduct.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductDeleteRequest {
    @NotNull
    private String storeProductId;
}
