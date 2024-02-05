package com.ecommerce.storeadmin.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    /**
     * Jackson2JsonMessageConverter를 생성해 JSON 형식의 메시지를 처리하는 객체 정의
     * (application.yaml에 rabbitmq host, port, username, password를 통해 ConnectionFactory 생성)
     * @param objectMapper JSON 변환을 위한 ObjectMapper 객체
     * @return 생성된 Jackson2JsonMessageConverter 객체
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
