package com.ecommerce.product.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User Error는 2000번대 errorCode 사용
 */
@AllArgsConstructor
@Getter
public enum StoreProductErrorCode implements ErrorCodeIfs {
    STORE_PRODUCT_ENTITY_NULL_POINT_ERROR(HttpStatus.NOT_FOUND.value(), 2400, "상품 엔티티 null"),
    STORE_PRODUCT_NULL_POINT_ERROR(HttpStatus.NOT_FOUND.value(), 2400, "상품 null"),
    STORE_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 2404, "상품을 찾을 수 없음"),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 2404, "상점을 찾을 수 없음"),
    STORE_PRODUCT_CREATE_FAIL(HttpStatus.NOT_FOUND.value(), 2500, "상품 생성 실패"),
    STORE_PRODUCT_UPDATE_FAIL(HttpStatus.NOT_FOUND.value(), 2500, "상품 수정 실패"),
    STORE_PRODUCT_DELETE_FAIL(HttpStatus.NOT_FOUND.value(), 2500, "상품 삭제 실패"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeNumber;
    private final String description;
}
