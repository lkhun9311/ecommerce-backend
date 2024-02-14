package com.ecommerce.storeadmin.domain.sse.controller;

import com.ecommerce.storeadmin.common.error.ErrorCode;
import com.ecommerce.storeadmin.common.exception.ApiException;
import com.ecommerce.storeadmin.domain.authorization.model.UserSession;
import com.ecommerce.storeadmin.domain.sse.connection.SseConnectionPool;
import com.ecommerce.storeadmin.domain.sse.connection.model.UserSseConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {

    private final SseConnectionPool sseConnectionPool;
    private final ObjectMapper objectMapper;

    // SSE 연결 요청을 처리하는 메서드
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(
            @Parameter(hidden = true)
            @AuthenticationPrincipal
            UserSession userSession
    ){
        // 로그인 사용자 정보 로깅
        log.info("[connection] login user {}", userSession);

        // SSE 연결
        UserSseConnection userSseConnection = UserSseConnection.connect(
                userSession.getUserId().toString(),
                sseConnectionPool,
                objectMapper
        );

        // 연결된 세션을 Connection Pool에 추가
        sseConnectionPool.addSession(userSseConnection.getUniqueKey(), userSseConnection);

        return userSseConnection.getSseEmitter();
    }

    // 이벤트를 전송하는 메서드
    @GetMapping( "/push-event")
    public void pushEvent(
            @Parameter(hidden = true)
            @AuthenticationPrincipal
            UserSession userSession
    ){
        // Connection Pool에서 사용자의 세션 조회
        UserSseConnection userSseConnection = sseConnectionPool.getSession(userSession.getUserId().toString());

        // 세션이 존재하면 이벤트 전송
        Optional.ofNullable(userSseConnection)
                .ifPresentOrElse(
                        it -> it.sendEvent("event push"),
                        () -> {
                            throw new ApiException(ErrorCode.NULL_POINT_ERROR, "user sse connection not found");
                        }
                );
    }
}

