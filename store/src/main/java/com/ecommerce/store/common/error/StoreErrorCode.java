package com.ecommerce.store.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Store Error는 2000번대 errorCode 사용
 */
@AllArgsConstructor
@Getter
public enum StoreErrorCode implements ErrorCodeIfs {
    STORE_ENTITY_NULL_POINT_ERROR(HttpStatus.BAD_REQUEST.value(), 2400, "상점 엔티티 null"),
    STORE_NULL_POINT_ERROR(HttpStatus.BAD_REQUEST.value(), 2400, "상점 null"),
    STORE_NAME_DOUBLED(HttpStatus.BAD_REQUEST.value(), 2400, "동일한 이름을 가진 상점이 이미 존재합니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 2404, "상점을 찾을 수 없음"),
    STORE_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), 2500, "상점 생성 실패"),
    STORE_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), 2500, "상점 수정 실패"),
    STORE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), 2500, "상점 삭제 실패"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeNumber;
    private final String description;
}
