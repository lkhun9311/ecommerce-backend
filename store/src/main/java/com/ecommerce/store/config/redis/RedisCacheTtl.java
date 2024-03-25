package com.ecommerce.store.config.redis;

// Redis 캐시 TTL 설정
public class RedisCacheTtl {

    private RedisCacheTtl() {}
    public static final int DEFAULT_EXPIRE_SEC = 30;
    public static final int STORE_CATEGORY_EXPIRE_SEC = 20;
    public static final int STORE_ID_EXPIRE_SEC = 40;
    public static final int STORE_EXPIRE_SEC = 40;
}
