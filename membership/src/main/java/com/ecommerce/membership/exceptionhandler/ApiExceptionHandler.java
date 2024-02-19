package com.ecommerce.membership.exceptionhandler;

import com.ecommerce.membership.common.api.Api;
import com.ecommerce.membership.common.error.ErrorCodeIfs;
import com.ecommerce.membership.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE) // 가장 먼저 실행
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException (
            ApiException apiException
    ){
        log.error("", apiException);

        ErrorCodeIfs errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(Api.error(errorCode, apiException.getErrorDescription()));
    }
}
