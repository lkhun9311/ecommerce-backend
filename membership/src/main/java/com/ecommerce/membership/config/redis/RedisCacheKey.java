package com.ecommerce.membership.config.redis;

// Redis 캐시 Key 설정
public class RedisCacheKey {

    private RedisCacheKey() {}
    public static final String USER_ME = "UserMe";
    public static final String JWT_TOKEN = "JwtToken";
}
