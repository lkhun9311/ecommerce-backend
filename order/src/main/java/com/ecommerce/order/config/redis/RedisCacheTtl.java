package com.ecommerce.order.config.redis;

// Redis 캐시 TTL 설정
public class RedisCacheTtl {

    private RedisCacheTtl() {}
    public static final int DEFAULT_EXPIRE_SEC = 30;
    public static final int STORE_PRODUCT_REGISTER_EXPIRE_SEC = 20;
    public static final int STORE_PRODUCT_SEARCH_STORE_ID_EXPIRE_SEC = 40;
}
