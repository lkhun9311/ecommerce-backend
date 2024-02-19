package com.ecommerce.apigateway.config.route;

import com.ecommerce.apigateway.filter.ServicePrivateApiFilter;
import com.ecommerce.apigateway.filter.ServicePublicApiFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Cloud Gateway에서 Public API와 Private API를 처리하는 라우팅 설정
 */
@Configuration
public class RouteStoreConfig {

    private final ServicePublicApiFilter servicePublicApiFilter;
    private final ServicePrivateApiFilter servicePrivateApiFilter;

    public RouteStoreConfig(ServicePublicApiFilter servicePublicApiFilter, ServicePrivateApiFilter servicePrivateApiFilter) {
        this.servicePublicApiFilter = servicePublicApiFilter;
        this.servicePrivateApiFilter = servicePrivateApiFilter;
    }

    /**
     * Store Public API Route 설정
     */
    @Bean
    public RouteLocator gatewayStorePublicRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(spec ->
                        spec.order(-1) // 필터 우선 순위 지정
                                .path("/store-api/open-api/**") // Public API 경로 정의
                                .filters(filterSpec ->
                                        filterSpec.filter(servicePublicApiFilter.apply(new ServicePublicApiFilter.Config())) // 필터 적용
                                                .rewritePath("/store-api(?<segment>/?.*)", "${segment}") // Public API 경로 재작성
                                )
                                .uri("http://localhost:8082") // 라우팅할 URI 정의
                )
                .build();
    }

    /**
     * Store Private API Route 설정
     */
    @Bean
    public RouteLocator gatewayStorePrivateRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(spec ->
                        spec.order(-1) // 필터 우선 순위 지정
                                .path("/store-api/api/**") // Private API 경로 정의
                                .filters(filterSpec ->
                                        filterSpec.filter(servicePrivateApiFilter.apply(new ServicePrivateApiFilter.Config())) // 필터 적용
                                                .rewritePath("/store-api(?<segment>/?.*)", "${segment}") // Private API 경로 재작성
                                )
                                .uri("http://localhost:8082") // 라우팅할 URI 정의
                )
                .build();
    }
}
