package com.ecommerce.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final List<String> swagger = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    /**
     * Spring Security의 필터 체인을 설정하는 Bean
     *
     * @param httpSecurity HttpSecurity 객체 (Spring Security의 설정 담당)
     * @return SecurityFilterChain 객체
     * @throws Exception 예외 처리
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // CSRF(Cross-Site Request Forgery, 사이트 간 요청 위조) 보호 비활성화
                .authorizeHttpRequests(it -> // HTTP 요청에 대한 인가 설정
                    it
                            // 정적 리소스에 대한 요청 모두 허용
                            .requestMatchers( // 특정 요청 매처에 대한 권한 설정
                                    PathRequest.toStaticResources().atCommonLocations()
                            ).permitAll()
                            // Swagger API에 대한 요청 모두 허용
                            .mvcMatchers( // MVC 패턴에 따른 매처 설정, 허용할 권한 부여
                                    swagger.toArray(String[]::new)
                            ).permitAll()
                            // "/open-api/**", "/login" 패턴에 대한 요청 모두 허용
                            .mvcMatchers(
                                    "/open-api/**",
                                    "/login"
                            ).permitAll()
                            // 그 외의 모든 요청은 인증된 사용자에게만 허용
                            .anyRequest().authenticated()
                )
                // custom 로그인 폼 사용 설정
                .formLogin()
                // 로그인 페이지 URL 지정
                .loginPage("/login")
                // 로그인 폼에서 username 입력 필드의 이름 지정
                .usernameParameter("email")
                // 로그인 폼에서 password 입력 필드의 이름 지정
                .passwordParameter("password")
                // 로그인 성공 시 이동할 URL 지정
                .defaultSuccessUrl("http://localhost:9090/store-admin-api/", true)
        ;

        return httpSecurity.build();
    }

    /**
     * BCrypt 해시 함수를 사용해 비밀번호를 안전하게 저장하고 검증
     * 해시된 비밀번호에 salt를 추가해 강력한 보안 제공
     * salt는 같은 비밀번호이더라도 서로 다른 salt 값을 사용해 레인보우 테이블 공격을 방어
     * 내부에서 자동으로 salt를 생성하므로 개발자가 별도로 salt를 관리할 필요가 없음
     * BCrypt는 해시값을 매번 다르게 생성
     * 동일한 입력에 대해 동일한 해시값이 생성되지 않도록 함으로써 해시 공격에 대응
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
