package com.ecommerce.api.interceptor;

import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.error.TokenErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.token.business.TokenBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Autorization Interceptor url : {}", request.getRequestURI());

        // 웹 브라우저에서 CORS를 사용하는 경우 실제로 요청(get, post)을 보내기 이전에 OPTIONS 메소드로 사전 검사 요청 => 전부 통과(pass)하도록 설정
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 정적 리소스(html, js, png 등)를 요청하는 경우 토큰 검증과 관련 없음 => 전부 통과(pass)하도록 설정
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // HTTP 요청 헤더에 JWT Token을 추출하고 사용자 인증
        String accessToken = request.getHeader("authorization-token");
        if (accessToken == null) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        // JWT Token을 통해 userId를 확인하고 유효성 검사
        Long userId = tokenBusiness.validateAccessToken(accessToken);

        if (userId != null) {
            // RequestContextHolder => HTTP 요청을 처리하면서 발생하는 데이터와 상태를 스레드 로컬(ThreadLocal)에 저장하고 검색, 전역적으로 공유
            // 현재 요청과 관련된 RequestAttributes 객체(requestContext) 가져옴
            RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());

            // RequestAttributes에 데이터(userId) 저장
            // 이 데이터의 범위(scope)를 현재 요청(request)으로 지정 즉, 데이터가 현재 HTTP 요청 범위 내에서만 데이터 유효
            // 현재 HTTP 요청 범위 내에서만 필요한 데이터를 저장하고, 여러 컴포넌트에서 데이터를 공유하거나 활용
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
            return true;
        }

        //토큰 검증 실패 시 ApiException 발생
        throw new ApiException(ErrorCode.BAD_REQUEST, "토큰 인증 실패");
    }
}
