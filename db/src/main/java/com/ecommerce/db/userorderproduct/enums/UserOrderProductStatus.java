package com.ecommerce.db.userorderproduct.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderProductStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    private final String description;
}
