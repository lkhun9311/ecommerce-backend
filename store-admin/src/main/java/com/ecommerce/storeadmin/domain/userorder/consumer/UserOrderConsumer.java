package com.ecommerce.storeadmin.domain.userorder.consumer;

import com.ecommerce.storeadmin.common.message.UserOrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOrderConsumer {

    /**
     * RabbitMQ Queue "ecommerce.queue"에서 주문 메시지 처리(consume)
     * @RabbitListener 어노테이션은 지정된 Queue에 메시지가 도착할 때마다 해당 메서드를 자동으로 호출
     * @param message UserOrderMessage 객체로 매핑되는 메시지
     *                RabbitMQ Queue에서 수신한 주문 메시지 정보 포함
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ecommerce.queue", durable = "true"),
            exchange = @Exchange(value = "ecommerce.exchange"),
            key = "ecommerce.key"
    ))
    public void receiveOrder(UserOrderMessage message) { // 메시지는 UserOrderMessage 객체로 매핑되어 파라미터로 전달

        log.info("rabbitmq consume >> {}", message);
    }
}
