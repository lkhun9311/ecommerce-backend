package com.ecommerce.product.config.redis;

// Redis 캐시 Key 설정
public class RedisCacheKey {

    private RedisCacheKey() {}
    public static final String STORE_PRODUCT_REGISTER = "StoreProductRegister";
    public static final String STORE_PRODUCT_SEARCH_STORE_ID = "StoreProductSearchStoreId";
}
