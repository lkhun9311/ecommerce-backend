package com.ecommerce.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Autorization Interceptor url : {}", request.getRequestURI());

        // web, chrome의 경우 get, post API를 요청하기 전에 option API를 요청해서 해당 메소드를 지원하는 지 체크 => 전부 통과(pass)하도록 설정
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // html, js, png 등 리소스를 요청하는 경우 => 전부 통과(pass)하도록 설정
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // TODO header 인증

        return true;
    }
}
