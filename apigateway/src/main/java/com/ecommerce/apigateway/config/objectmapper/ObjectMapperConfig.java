package com.ecommerce.apigateway.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 생성

        // 모듈 등록
        objectMapper.registerModule(new Jdk8Module()); // Java 8의 새로운 기능 지원
        objectMapper.registerModule(new JavaTimeModule()); // Java 8의 날짜와 시간 API 지원

        // 역직렬화 옵션 구성
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //  알 수 없는 속성이 있을 때 역직렬화 실패 방지
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 빈 객체를 직렬화할 때 실패하지 않음
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 날짜를 타임스탬프로 직렬화하지 않음

        // 속성(Property) 이름을 스네이크 케이스로 설정
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        return objectMapper;
    }
}
