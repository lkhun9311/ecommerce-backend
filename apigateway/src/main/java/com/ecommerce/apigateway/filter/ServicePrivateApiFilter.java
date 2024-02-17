package com.ecommerce.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * Spring Cloud Gateway에서 Private API 요청을 처리하는 사용자 정의 필터
 */
@Slf4j
@Component
public class ServicePrivateApiFilter extends AbstractGatewayFilterFactory<ServicePrivateApiFilter.Config> {

    /**
     * (필요한 경우) 구성 속성 정의
     */
    public static class Config {}

    /**
     * 필터 생성자
     */
    public ServicePrivateApiFilter() {

        super(Config.class);
    }

    /**
     * 필터 로직 정의
     *
     * @param config 구성 속성
     * @return GatewayFilter
     */
    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            String uri = exchange.getRequest().getURI().toString(); // 들어오는 요청 URI
            log.info("Service Private API filter route URI: {}", uri);

            // 필터 체인 계속 진행
            return chain.filter(exchange);
        };
    }
}
