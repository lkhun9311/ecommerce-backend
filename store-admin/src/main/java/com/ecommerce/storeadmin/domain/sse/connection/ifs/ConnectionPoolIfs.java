package com.ecommerce.storeadmin.domain.sse.connection.ifs;

/**
 * Connection Pool 인터페이스.
 * @param <T> 고유 키의 타입
 * @param <R> 세션의 타입
 */
public interface ConnectionPoolIfs<T, R> {

    /**
     * 세션을 Connection Pool에 추가
     * @param uniqueKey 고유 키
     * @param session 세션
     */
    void addSession(T uniqueKey, R session);

    /**
     * 주어진 고유 키에 해당하는 세션 반환
     * @param uniqueKey 고유 키
     * @return 해당하는 세션
     */
    R getSession(T uniqueKey);

    /**
     * 세션이 완료될 때 호출될 콜백 처리
     * @param session 완료된 세션
     */
    void onComplettionCallback(R session);
}
