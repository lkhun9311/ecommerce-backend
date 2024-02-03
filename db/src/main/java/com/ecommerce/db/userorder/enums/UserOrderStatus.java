package com.ecommerce.db.userorder.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ORDER("주문"),
    ACCEPT("주문 확인"),
    DELIVERY("배송 중"),
    RECEIVE("배송 완료"),
    ;

    private final String description;
}
