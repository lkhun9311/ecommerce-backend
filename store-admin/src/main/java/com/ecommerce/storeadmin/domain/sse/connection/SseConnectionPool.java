package com.ecommerce.storeadmin.domain.sse.connection;

import com.ecommerce.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import com.ecommerce.storeadmin.domain.sse.connection.model.UserSseConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SseConnectionPool implements ConnectionPoolIfs<String, UserSseConnection> {

    // 각 사용자의 연결을 추적하기 위한 맵
    // 여러 스레드에서 접근 가능
    // 요청이 들어올 때마다 새로운 스레드에서 처리
    private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    // 세션을 Connection Pool에 추가
    @Override
    public void addSession(String uniqueKey, UserSseConnection userSseConnection) {
        connectionPool.put(uniqueKey, userSseConnection);
    }

    // 주어진 고유 키에 해당하는 세션 반환
    @Override
    public UserSseConnection getSession(String uniqueKey) {
        return connectionPool.get(uniqueKey);
    }

    // 세션이 완료될 때 호출될 콜백 처리
    @Override
    public void onComplettionCallback(UserSseConnection session) {
        log.info("call back connection pool completed : {}", session);
        connectionPool.remove(session.getUniqueKey());
    }
}
