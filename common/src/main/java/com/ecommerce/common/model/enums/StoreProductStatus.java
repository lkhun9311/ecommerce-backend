package com.ecommerce.common.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreProductStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    private final String description;
}
