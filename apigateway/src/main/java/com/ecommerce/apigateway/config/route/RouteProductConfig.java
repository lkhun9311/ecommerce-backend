package com.ecommerce.apigateway.config.route;

import com.ecommerce.apigateway.filter.ServicePrivateApiFilter;
import com.ecommerce.apigateway.filter.ServicePublicApiFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Cloud Gateway에서 Public API와 Private API를 처리하는 라우팅 설정
 */
@Configuration
public class RouteProductConfig {

    private final ServicePublicApiFilter servicePublicApiFilter;
    private final ServicePrivateApiFilter servicePrivateApiFilter;

    public RouteProductConfig(ServicePublicApiFilter servicePublicApiFilter, ServicePrivateApiFilter servicePrivateApiFilter) {
        this.servicePublicApiFilter = servicePublicApiFilter;
        this.servicePrivateApiFilter = servicePrivateApiFilter;
    }

    /**
     * Product Public API Route 설정
     */
    @Bean
    public RouteLocator gatewayProductPublicRoutes(RouteLocatorBuilder builder, @Value("${service.product.url}") String productServiceUrl) {
        return builder.routes()
                .route(spec ->
                        spec.order(-1) // 필터 우선 순위 지정
                                .path("/product-api/open-api/**") // Public API 경로 정의
                                .filters(filterSpec ->
                                        filterSpec.filter(servicePublicApiFilter.apply(new ServicePublicApiFilter.Config())) // 필터 적용
                                                .rewritePath("/product-api(?<segment>/?.*)", "${segment}") // Public API 경로 재작성
                                )
                                .uri(productServiceUrl) // 라우팅할 URI 정의
                )
                .build();
    }

    /**
     * Product Private API Route 설정
     */
    @Bean
    public RouteLocator gatewayProductPrivateRoutes(RouteLocatorBuilder builder, @Value("${service.product.url}") String productServiceUrl) {
        return builder.routes()
                .route(spec ->
                        spec.order(-1) // 필터 우선 순위 지정
                                .path("/product-api/api/**") // Private API 경로 정의
                                .filters(filterSpec ->
                                        filterSpec.filter(servicePrivateApiFilter.apply(new ServicePrivateApiFilter.Config())) // 필터 적용
                                                .rewritePath("/product-api(?<segment>/?.*)", "${segment}") // Private API 경로 재작성
                                )
                                .uri(productServiceUrl) // 라우팅할 URI 정의
                )
                .build();
    }
}
