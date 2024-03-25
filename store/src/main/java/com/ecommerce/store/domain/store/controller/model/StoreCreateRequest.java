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
public class StoreCreateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private StoreCategory category;

    @NotBlank
    private String thumbnailUrl;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private Boolean isDoubleChecked;
}
