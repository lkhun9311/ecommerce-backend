package com.ecommerce.store.domain.store.controller.model;

import com.ecommerce.common.model.enums.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreUpdateRequest {
    @NotNull
    private String storeId;

    @NotBlank
    private String name;

    @NotNull
    private String address;

    @NotBlank
    private StoreCategory category;

    @NotBlank
    private double star;

    @NotBlank
    private String thumbnailUrl;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private Boolean isDoubleChecked;
}
