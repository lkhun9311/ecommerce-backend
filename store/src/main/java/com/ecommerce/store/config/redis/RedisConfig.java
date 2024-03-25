package com.ecommerce.store.config.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration // Bean 구성
@EnableCaching // Spring의 캐싱 지원 활성화
public class RedisConfig {

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper redisObjectMapper = new ObjectMapper();
        redisObjectMapper.registerModule(new Jdk8Module()); // Java 8의 새로운 기능 지원
        redisObjectMapper.registerModule(new JavaTimeModule()); // Java 8의 날짜와 시간 API 지원
        redisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알 수 없는 속성이 있을 때 역직렬화 실패 방지
        redisObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 빈 객체를 직렬화할 때 실패하지 않음
        redisObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 날짜를 타임스탬프로 직렬화하지 않음
        redisObjectMapper.activateDefaultTyping( // 다형성 활성화, 역직렬화 시 여러 타입 지원
                redisObjectMapper.getPolymorphicTypeValidator(), // 타입 메타데이터 활성화, 타입 메타데이터는 객체를 직렬화하고 역직렬화 시 객체의 실제 타입 식별(다양한 하위 클래스를 포함하는 리스트를 직렬화하고 역직렬화 시 유용)
                ObjectMapper.DefaultTyping.NON_FINAL, // 보안, 성능 상의 이유로 최종 하위 클래스의 타입 메타데이터만 유지하도록 설정(상속 계층 구조의 최하위 클래스만 포함)
                JsonTypeInfo.As.PROPERTY // 타입 정보를 JSON 속성으로 지정
        );
        return redisObjectMapper;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, ObjectMapper redisObjectMapper) {

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration // Redis 캐시 설정
                .defaultCacheConfig() // 기본 캐시 설정
                .entryTtl(Duration.ofSeconds(RedisCacheTtl.DEFAULT_EXPIRE_SEC)) // 캐시 TTL 설정
                .serializeValuesWith( // 캐시 Value 직렬화 설정
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer(redisObjectMapper) // Jackson의 기본 타입 정보(default typing)로 objectMapper 사용
                        )
                );

        Map<String, RedisCacheConfiguration> customCacheConfig = new HashMap<>(); // 사용자 정의 캐시 설정

        // StoreCategory 캐시 TTL 설정
        customCacheConfig.put(RedisCacheKey.STORE_CATEGORY, defaultCacheConfig
                .entryTtl(Duration.ofSeconds(RedisCacheTtl.STORE_CATEGORY_EXPIRE_SEC))); // 사용자 정의 캐시 TTL 설정

        // StoreId 캐시 TTL 설정
        customCacheConfig.put(RedisCacheKey.STORE_ID, defaultCacheConfig
                .entryTtl(Duration.ofSeconds(RedisCacheTtl.STORE_ID_EXPIRE_SEC))); // 사용자 정의 캐시 TTL 설정

        // Store 캐시 TTL 설정
        customCacheConfig.put(RedisCacheKey.STORE, defaultCacheConfig
                .entryTtl(Duration.ofSeconds(RedisCacheTtl.STORE_EXPIRE_SEC))); // 사용자 정의 캐시 TTL 설정

        // Redis 캐시 관리자 설정
        return RedisCacheManager
                .builder(redisConnectionFactory) // Redis 연결에 redisConnectionFactory 사용
                .cacheDefaults(defaultCacheConfig) // 기본 캐시 구성에 defaultCacheConfig 설정, 동적으로 생성된 Redis 캐시에 적용됨
                .withInitialCacheConfigurations(customCacheConfig) // 사용자 정의 캐시 구성에 customCacheConfig 설정
                .build();
    }
}
