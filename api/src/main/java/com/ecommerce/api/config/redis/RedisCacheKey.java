package com.ecommerce.api.config.redis;

// Redis 캐시 Key 설정
public class RedisCacheKey {

    private RedisCacheKey() {}
    public static final String USER_ORDER_CURRENT = "UserOrderCurrent";
    public static final String USER_ORDER_HISTORY = "UserOrderHistory";
    public static final String USER_ORDER_READ = "UserOrderRead";
}
