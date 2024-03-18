package com.ecommerce.order.domain.userorder.producer;

import com.ecommerce.order.common.message.UserOrderMessage;
import com.ecommerce.order.common.rabbitmq.Producer;
import com.ecommerce.order.entity.UserOrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOrderProducer {

    private final Producer producer;
    private static final String EXCHANGE = "ecommerce.exchange";
    private static final String ROUTE_KEY = "ecommerce.key";

    /**
     * 주문 정보를 받아 해당 주문에 대한 메시지를 생성하고 RabbitMQ로 전송 (produce)
     * @param userOrderEntity 주문 정보를 담은 UserOrderEntity 객체
     */
    public void sendOrder(UserOrderEntity userOrderEntity){
        sendOrder(userOrderEntity.getId());
    }

    /**
     * 주문 ID를 받아 해당 주문에 대한 메시지를 생성하고 RabbitMQ로 전송
     * @param userOrderId 주문 ID
     */
    public void sendOrder(Long userOrderId){
        // UserOrderMessage를 생성하고, 주문 ID 설정
        UserOrderMessage message = UserOrderMessage.builder()
                .userOrderId(userOrderId)
                .build();

        // producer를 이용해 RabbitMQ에 메시지 전송 (produce)
        producer.produce(EXCHANGE, ROUTE_KEY, message);
        log.info("rabbitmq produce >> {}", message);
    }
}
