package com.ecommerce.apigateway.filter;

import com.ecommerce.apigateway.common.error.TokenErrorCode;
import com.ecommerce.apigateway.common.exception.ApiException;
import com.ecommerce.apigateway.membership.model.TokenValidateDto;
import com.ecommerce.apigateway.membership.model.TokenValidateRequest;
import com.ecommerce.apigateway.membership.model.TokenValidateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    // 한글 문자열을 Unicode로 변환하는 메서드
    private String toUnicode(String str) {
        StringBuilder unicode = new StringBuilder();
        for (char character : str.toCharArray()) {
            unicode.append("\\u").append(String.format("%04x", (int) character));
        }
        return unicode.toString();
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
            java.net.URI uri = exchange.getRequest().getURI();
            log.info("[Service Private API Filter] route URI: {}", uri);

            // membership 서비스를 통해 사용자 인증
            // 1. JWT 토큰 유무 확인
            List<String> headers = exchange.getRequest().getHeaders().getOrDefault("authorization-token", Collections.emptyList());
            String token;
            if (headers.isEmpty()) {
                throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
            } else {
                token = headers.get(0);
            }
            log.info("[Service Private API Filter] authorization token : {}", token);

            // 2. 회원 서비스에서 JWT 토큰 유효성 검사
            String membershipApiUrl = UriComponentsBuilder
                    .fromUriString("http://localhost")
                    .port(8081)
                    .path("/validation-api/token/validation")
                    .build()
                    .encode()
                    .toUriString();

            //WebFlux 기반이므로 WebClient를 사용해 HTTP 요청 전송
            WebClient webClient = WebClient.builder().baseUrl(membershipApiUrl).build();

            TokenValidateDto tokenValidateDto = new TokenValidateDto(token);
            TokenValidateRequest request = new TokenValidateRequest(tokenValidateDto);
            log.info("[Service Private API Filter] request : {}", request);

            return webClient
                    .post()
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    // HTTP 상태 코드가 에러인 경우, 에러 응답 처리
                    .onStatus(
                            HttpStatus::isError,
                            response -> response.bodyToMono(Object.class)
                                    .flatMap(error -> {
                                        log.error("[Service Private API Filter] error : {}", error);
                                        return Mono.error(new ApiException(TokenErrorCode.TOKEN_EXCEPTION));
                                    })
                    )
                    // HTTP 상태 코드가 성공인 경우, 응답을 TokenValidateResponse 객체로 변환
                    .bodyToMono(TokenValidateResponse.class)
                    .flatMap(response -> {
                                log.info("[Service Private API Filter] response : {}", response);

                        // 3. 사용자 정보를 Header에 추가
                        String userId = response.getUserId() != null ? response.getUserId().toString() : null;
                        String name = response.getName() != null ? response.getName() : null;
                        String email = response.getEmail() != null ? response.getEmail() : null;
                        String status = response.getStatus() != null ? response.getStatus().toString() : null;
                        String address = response.getAddress() != null ? response.getAddress() : null;

                        ServerHttpRequest requestProxy = exchange.getRequest().mutate()
                                .header("x-user-id", Objects.requireNonNull(userId))
                                .header("x-user-name", toUnicode(Objects.requireNonNull(name)))
                                .header("x-user-email", toUnicode(Objects.requireNonNull(email)))
                                .header("x-user-status", Objects.requireNonNull(status))
                                .header("x-user-address", toUnicode(Objects.requireNonNull(address)))
                                .build();

                        ServerWebExchange requestBuild = exchange.mutate().request(requestProxy).build();

                        // 목적지로 필터 체인 진행
                        return chain.filter(requestBuild);
                    })
                    .onErrorMap(error -> {
                        log.error("[Service Private API Filter] error : ", error);
                        return error;
                    });
        };
    }
}
