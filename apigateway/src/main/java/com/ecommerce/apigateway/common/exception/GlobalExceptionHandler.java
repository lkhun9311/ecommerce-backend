package com.ecommerce.apigateway.common.exception;

import com.ecommerce.apigateway.common.error.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    // 생성자를 통해 ObjectMapper 주입
    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 에러 응답을 내보내는 ErrorResponse 클래스 정의
    @AllArgsConstructor
    @Getter
    public static class ErrorResponse {
        private final String error; // 에러 메세지
    }

    // ErrorWebExceptionHandler 인터페이스의 handle 메서드 구현
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        log.error("[Global Error Exception] url : {}", exchange.getRequest().getURI(), ex);

        // 이미 응답이 커밋되었는지 확인
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex); // 이미 응답이 커밋된 경우 예외 발생
        }

        // 응답의 Content-Type을 application/json으로 설정
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 에러 응답 객체 생성
        ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());

        // ErrorResponse 객체를 JSON으로 직렬화해 byte 배열로 변환
        byte[] errorResponseByteArray;
        try {
            errorResponseByteArray = objectMapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            // 직렬화 과정에서 예외가 발생하면 SerializationException 던짐
            throw new ApiException(ErrorCode.SERIALIZATION_ERROR, e);
        }

        // 응답에 전송할 DataBuffer 생성
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        DataBuffer dataBuffer = dataBufferFactory.wrap(errorResponseByteArray);

        // 응답에 DataBuffer 입력
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }
}
