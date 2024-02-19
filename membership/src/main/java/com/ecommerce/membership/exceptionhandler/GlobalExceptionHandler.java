package com.ecommerce.membership.exceptionhandler;

import com.ecommerce.membership.common.api.Api;
import com.ecommerce.membership.common.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE) // 가장 마지막에 실행
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(
            Exception exception
    ) {
        log.error("", exception);

        return ResponseEntity
                .status(500)
                .body(Api.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
