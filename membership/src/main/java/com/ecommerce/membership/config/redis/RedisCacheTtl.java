package com.ecommerce.membership.config.redis;

// Redis 캐시 TTL 설정
public class RedisCacheTtl {

    private RedisCacheTtl() {}
    public static final int DEFAULT_EXPIRE_SEC = 30;
    public static final int USER_ME_EXPIRE_SEC = 20;
    public static final int JWT_TOKEN_EXPIRE_SEC = 3600;
}
