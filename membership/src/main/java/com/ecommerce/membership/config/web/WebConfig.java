package com.ecommerce.membership.config.web;

import com.ecommerce.membership.interceptor.AuthorizationInterceptor;
import com.ecommerce.membership.resolver.UserSessionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor; // AuthorizationInterceptor가 @Component이므로 주입 가능
    private final UserSessionResolver userSessionResolver;

    // 회원가입, 약관과 같이 사용자 인증 처리하지 않아도 되는 API => 인증에서 제외(exclude) 처리
    private final List<String> openApi = List.of(
            "/open-api/**",
            "/validation-api/token/**"
    );

    private final List<String> defaultExclude = List.of(
            "/",
            "/error",
            "favicon.ico"
    );

    private final List<String> swagger = List.of(
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

    // userSessionResolver를 Bean으로 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionResolver);
    }
}
