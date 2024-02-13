package com.ecommerce.storeadmin.domain.sse.connection.model;

import com.ecommerce.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;
    private final ObjectMapper objectMapper;

    /**
     * 사용자 SSE 연결 생성자
     * @param uniqueKey 고유 키
     * @param connectionPoolIfs 연결 풀 인터페이스
     * @param objectMapper ObjectMapper 객체
     */
    private UserSseConnection(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper
    ) {
        // 필드 초기화
        this.uniqueKey = uniqueKey;
        this.sseEmitter = new SseEmitter(60 * 1000L);
        this.connectionPoolIfs = connectionPoolIfs;
        this.objectMapper = objectMapper;

        // SSE Emitter의 완료 이벤트 처리
        this.sseEmitter.onCompletion(() -> {
            // Connection Pool에서 해당 세션 제거
            this.connectionPoolIfs.onComplettionCallback(this);
            log.info("SSE Complete");
        });

        // SSE Emitter의 타임아웃 이벤트 처리
        this.sseEmitter.onTimeout(() -> {
            // SSE Emitter 완료 처리
            this.sseEmitter.complete();
            log.info("SSE Time Out");
        });

        // SSE 연결 성공 이벤트 전송
        this.sendEvent("onopen", "connect");
    }


    /**
     * 사용자 SSE 연결 생성
     * @param uniqueKey 사용자를 식별하는 고유 키
     * @param connectionPoolIfs 연결 풀 인터페이스
     * @param objectMapper JSON 변환기
     * @return 생성된 사용자 SSE 연결
     */
    public static UserSseConnection connect(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper
    ){
        // 사용자 SSE 연결 생성 후 반환
        return new UserSseConnection(uniqueKey, connectionPoolIfs, objectMapper);
    }

    /**
     * 데이터를 이벤트로 전송
     * @param data 전송할 데이터
     */
    public void sendEvent(
            Object data
    ) {
        try {
            // 데이터를 JSON 형태로 변환
            String json = objectMapper.writeValueAsString(data);

            // SSE 이벤트 생성
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .data(json);

            // SSE 이벤트 전송
            this.sseEmitter.send(event);
        } catch (Exception e) {
            // 오류 발생 시 완료 처리
            this.sseEmitter.completeWithError(e);
        }
    }

    /**
     * 이벤트의 이름과 데이터를 이벤트로 전송
     * @param eventName 이벤트 이름
     * @param data 전송할 데이터
     */
    public void sendEvent(
            String eventName,
            Object data
    ){
        try {
            // 데이터를 JSON 형태로 변환
            String json = objectMapper.writeValueAsString(data);

            // SSE 이벤트 생성
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .name(eventName)
                    .data(json);

            // SSE 이벤트 전송
            this.sseEmitter.send(event);
        } catch (Exception e) {
            // 오류 발생 시 완료 처리
            this.sseEmitter.completeWithError(e);
        }
    }
}
