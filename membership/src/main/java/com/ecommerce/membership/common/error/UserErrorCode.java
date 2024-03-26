package com.ecommerce.membership.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User Error는 1000번대 errorCode 사용
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 1400, "사용자를 찾을 수 없음"),
    USER_NAME_DOUBLED(HttpStatus.BAD_REQUEST.value(), 1400, "동일한 이름을 가진 사용자가 이미 존재합니다."),
    USER_SESSTION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404, "사용자 세션을 찾을 수 없음"),
    REQUEST_CONTEXT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404, "Request Context Null Error"),
    X_USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404, "x-user-id를 찾을 수 없음"),
    USER_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1500, "사용자 생성 실패"),
    USER_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1500, "사용자 수정 실패"),
    USER_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1500, "사용자 삭제 실패"),
    USER_LOGIN_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1500, "사용자 로그인 실패"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeNumber;
    private final String description;
}
