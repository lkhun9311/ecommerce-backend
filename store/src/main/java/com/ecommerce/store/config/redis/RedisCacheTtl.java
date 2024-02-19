package com.ecommerce.store.config.redis;

// Redis 캐시 TTL 설정
public class RedisCacheTtl {

    private RedisCacheTtl() {}
    public static final int DEFAULT_EXPIRE_SEC = 30;
    public static final int STORE_REGISTER_EXPIRE_SEC = 20;
    public static final int STORE_SEARCH_CATEGORY_EXPIRE_SEC = 40;
}
