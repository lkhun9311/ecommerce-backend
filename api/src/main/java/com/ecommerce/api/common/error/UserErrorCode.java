package com.ecommerce.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User Error는 1000번대 errorCode 사용
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    USER_SESSTION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404, "사용자 세션을 찾을 수 없음"),
    REQUEST_CONTEXT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404, "Request Context Null Error"),
    X_USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404, "x-user-id를 찾을 수 없음"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeNumber;
    private final String description;
}
