package com.ecommerce.apigateway.config.route;

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
public class RouteStoreAdminConfig {

    private final ServicePublicApiFilter servicePublicApiFilter;

    public RouteStoreAdminConfig(ServicePublicApiFilter servicePublicApiFilter) {
        this.servicePublicApiFilter = servicePublicApiFilter;
    }

    /**
     * Store Admin Default API Route 설정
     */
    @Bean
    public RouteLocator gatewayStoreAdminDefaultRoutes(RouteLocatorBuilder builder, @Value("${service.store.admin.url}") String storeAdminServiceUrl) {
        return builder.routes()
                .route(spec ->
                        spec.order(-1) // 필터 우선 순위 지정
                                .path("/store-admin-api/**") // Public API 경로 정의
                                .filters(filterSpec ->
                                        filterSpec.filter(servicePublicApiFilter.apply(new ServicePublicApiFilter.Config())) // 필터 적용
                                                .rewritePath("/store-admin-api(?<segment>/?.*)", "${segment}") // Public API 경로 재작성
                                )
//                                .uri("http://localhost:8080") // 라우팅할 URI 정의
                                .uri(storeAdminServiceUrl) // 라우팅할 URI 정의
                )
                .build();
    }

    /**
     * Store Admin Public API Route 설정
     */
    @Bean
    public RouteLocator gatewayStoreAdminPublicRoutes(RouteLocatorBuilder builder, @Value("${service.store.admin.url}") String storeAdminServiceUrl) {
        return builder.routes()
                .route(spec ->
                        spec.order(-1) // 필터 우선 순위 지정
                                .path("/store-admin-api/open-api/**") // Public API 경로 정의
                                .filters(filterSpec ->
                                        filterSpec.filter(servicePublicApiFilter.apply(new ServicePublicApiFilter.Config())) // 필터 적용
                                                .rewritePath("/store-admin-api(?<segment>/?.*)", "${segment}") // Public API 경로 재작성
                                )
//                                .uri("http://localhost:8080") // 라우팅할 URI 정의
                                .uri(storeAdminServiceUrl) // 라우팅할 URI 정의
                )
                .build();
    }
}
