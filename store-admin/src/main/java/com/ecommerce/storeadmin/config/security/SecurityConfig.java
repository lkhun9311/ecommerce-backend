package com.ecommerce.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private List<String> swagger = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // CSRF(Cross-Site Request Forgery, 사이트 간 요청 위조) 보호 비활성화
                .authorizeHttpRequests(it -> { // HTTP 요청에 대한 인가 설정
                    it
                            // 정적 리소스에 대한 요청 모두 허용
                            .requestMatchers( // 특정 요청 매처에 대한 권한 설정
                                    PathRequest.toStaticResources().atCommonLocations()
                            ).permitAll()
                            // Swagger API에 대한 요청 모두 허용
                            .mvcMatchers( // MVC 패턴에 따른 매처 설정, 허용할 권한 부여
                                    swagger.toArray(new String[0])
                            ).permitAll()
                            // "/open-api/**" 패턴에 대한 요청 모두 허용
                            .mvcMatchers(
                                    "/open-api/**"
                            ).permitAll()
                            // 그 외의 모든 요청은 인증된 사용자에게만 허용
                            .anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults()); // 기본 로그인 폼 사용 설정
        return httpSecurity.build();
    }
}
