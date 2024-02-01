package com.ecommerce.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token Error는 2000번대 errorCode 사용
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), 2000, "토큰이 유효하지 않음"),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST.value(), 2001, "토큰이 만료됨"),
    TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST.value(), 2002, "알 수 없는 토큰 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 2003, "헤더에 인증 토큰 없음"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeNumber;
    private final String description;
}
