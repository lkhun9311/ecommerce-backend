package com.ecommerce.order.common.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * RabbitMQ Producer가 메시지를 생성하고, 지정된 교환(exchange)과 라우팅 키(routeKey)로 메시지 전송
     * @param exchange 메시지를 전송할 RabbitMQ 교환(exchange)의 이름
     * @param routeKey 메시지 라우팅을 위한 RabbitMQ 라우팅 키(routeKey)
     * @param object 전송할 메시지 내용
     */
    public void produce(String exchange, String routeKey, Object object){
        rabbitTemplate.convertAndSend(exchange, routeKey, object);
    }
}
