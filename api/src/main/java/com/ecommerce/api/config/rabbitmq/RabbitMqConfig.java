package com.ecommerce.api.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    /**
     * DirectExchange를 생성해 RabbitMQ에 사용할 교환(exchange) 정의
     * @return 생성된 DirectExchange 객체
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("ecommerce.exchange");
    }

    /**
     * Queue를 생성해 RabbitMQ에서 사용할 대기열(queue) 정의
     * @return 생성된 Queue 객체
     */
    @Bean
    public Queue queue(){
        return new Queue("ecommerce.queue");
    }

    /**
     * DirectExchange와 Queue를 연결하는 Binding 정의
     * @param directExchange DirectExchange 객체
     * @param queue Queue 객체
     * @return 생성된 Binding 객체
     */
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("ecommerce.key");
    }

    /**
     * RabbitTemplate을 생성해 RabbitMQ와 통신하는 데 사용할 객체 정의
     * @param connectionFactory RabbitMQ와의 연결을 관리하는 ConnectionFactory 객체 (application.yaml에 rabbitmq host, port, username, password 작성함)
     * @param messageConverter 메시지 변환을 위한 MessageConverter 객체
     * @return 생성된 RabbitTemplate 객체
     */
    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /**
     * Jackson2JsonMessageConverter를 생성해 JSON 형식의 메시지를 처리하는 객체 정의
     * @param objectMapper JSON 변환을 위한 ObjectMapper 객체
     * @return 생성된 Jackson2JsonMessageConverter 객체
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
