package com.ecommerce.api.config.web;

import com.ecommerce.api.interceptor.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor; // AuthorizationInterceptor가 @Component이므로 주입 가능

    // 회원가입, 약관과 같이 유저가 없어 인증 처리하지 않아도 되는 API => 인증에서 제외(exclude) 처리
    private List<String> openApi = List.of(
            "/open-api/**"
    );

    private List<String> defaultExclude = List.of(
            "/",
            "/error",
            "favicon.ico"
    );

    private List<String> swagger = List.of(
            "/swagger-ui/html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    // interceptor를 Bean으로 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(openApi)
                .excludePathPatterns(defaultExclude)
                .excludePathPatterns(swagger)
                ;
    }
}
