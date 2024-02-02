package com.ecommerce.db.storeproduct.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreProductStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    private String description;
}
