package com.ecommerce.api.config.health;

import com.ecommerce.api.common.rabbitmq.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api")
public class HealthOpenApiController {

    private final Producer producer;

    /**
     * health check
     * RabbitMQ Producer를 사용해 "hello" 메시지를 "ecommerce.exchange"와 "ecommerce.key"로 라우팅해 전송
     */
    @GetMapping("/health")
    public void health() {
        log.info("health call");
        producer.produce("ecommerce.exchange", "ecommerce.key", "hello");
    }
}
