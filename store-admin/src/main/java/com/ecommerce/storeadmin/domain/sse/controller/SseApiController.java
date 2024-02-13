package com.ecommerce.storeadmin.domain.sse.controller;

import com.ecommerce.storeadmin.domain.authorization.model.UserSession;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {

    // 각 사용자의 연결을 추적하기 위한 맵
    // 여러 스레드에서 접근 가능하고, 요청이 들어올 때마다 새로운 스레드에서 처리
    private static final Map<String, SseEmitter> userConnection = new ConcurrentHashMap<>();

    // SSE 연결 요청을 처리하는 메서드
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(
            @Parameter(hidden = true)
            @AuthenticationPrincipal
            UserSession userSession
    ){
        log.info("[connection] login user {}", userSession);

        // 새로운 SseEmitter 생성 및 사용자와 매핑
        // sse time out 60s 설정 (단위 : ms, 기본값 : 30s)
        SseEmitter emitter = new SseEmitter(60 * 1000L);
        userConnection.put(userSession.getUserId().toString(), emitter);

        // SSE 타임아웃 처리
        emitter.onTimeout(() -> {
            emitter.complete(); // emitter.onCompletion() 동작
            log.info("SSE Time Out");
        });

        // SSE 완료 처리 (연결 종료, complete)
        emitter.onCompletion(() -> {
            userConnection.remove(userSession.getUserId().toString());
            log.info("SSE Complete");
        });

        log.info("[connection] " + emitter);
        log.info("[connect] " + userSession.getUserId().toString());

        // SSE 연결 성공 응답 전송
        SseEmitter.SseEventBuilder event = SseEmitter.event()
                .data("connection success");

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // 이벤트를 전송하는 메서드
    @GetMapping( "/push-event")
    public void pushEvent(
            @Parameter(hidden = true)
            @AuthenticationPrincipal
            UserSession userSession
    ){
        // 현재 사용자와 연결된 SseEmitter 찾기
        SseEmitter emitter = userConnection.get(userSession.getUserId().toString());

        log.info("[received] " + emitter);
        log.info("[received] " + userSession.getUserId().toString());

        // 이벤트 생성
        SseEmitter.SseEventBuilder event = SseEmitter
                .event()
                .data("push event");

        // 이벤트 전송
        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}

