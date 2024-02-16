package com.ecommerce.api.config.redis;

// Redis 캐시 TTL 설정
public class RedisCacheTtl {

    private RedisCacheTtl() {}
    public static final int DEFAULT_EXPIRE_SEC = 30;
    public static final int USER_ORDER_CURRENT_EXPIRE_SEC = 20;
    public static final int USER_ORDER_HISTORY_EXPIRE_SEC = 40;
    public static final int USER_ORDER_READ_EXPIRE_SEC = 60;
}
