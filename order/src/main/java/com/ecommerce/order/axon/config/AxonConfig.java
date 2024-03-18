package com.ecommerce.order.axon.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    // JacksonSerializer를 사용해 메시지 직렬화/역직렬화 빈 생성
    @Bean
    public Serializer messageSerializer() {
        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // 알려지지 않은 속성에 대해 실패를 허용하지 않도록 설정
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 모든 클래스의 정보를 유지하도록 설정
        // Jackson 라이브러리 중 하나인 ObjectMapper는 객체를 직렬화할 때 클래스 정보를 포함 하지 않음
        // 보안 및 안정성 이유로 기본적으로 비활성화
        // 하지만 Axon Framework는 클래스 정보를 사용해 객체를 역직렬화하고 클래스의 type을 식별하기 위해 클래스 정보를 알아야 함
        objectMapper.activateDefaultTyping( // ObjectMapper 기본 타입 활성화
                objectMapper.getPolymorphicTypeValidator(), // Jackson이 객체의 클래스 정보를 보존하고 이를 역직렬화할 때 사용
                ObjectMapper.DefaultTyping.NON_FINAL // 최상위 클래스가 final로 선언되어 있지 않은 경우에만 JSON에 클래스 type 정보 포함
                // 만약 최상위 클래스가 final로 선언되어 있을 경우 Jackson은 클래스 type 정보를 포함하지 않음
        );

        // JacksonSerializer 설정
        return JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .build();
    }
}
