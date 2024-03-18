package com.ecommerce.store.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreCategory {

    WOMEN("여성"),
    MEN("남성"),
    CLOTHING("의류"),
    BAGS("가방"),
    SHOES("신발"),
    ACCESSORIES("악세서리"),
    ;

    private final String description;
}
