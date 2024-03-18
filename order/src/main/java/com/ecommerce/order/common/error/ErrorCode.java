package com.ecommerce.order.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {

    OK(HttpStatus.OK.value(), 200, "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NULL_POINT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point Error"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeNumber;
    private final String description;
}
