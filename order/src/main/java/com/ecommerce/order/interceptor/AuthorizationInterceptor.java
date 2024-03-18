package com.ecommerce.order.interceptor;

import com.ecommerce.order.common.error.UserErrorCode;
import com.ecommerce.order.common.exception.ApiException;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Autorization Interceptor url : {}", request.getRequestURI());

        // 웹 브라우저에서 CORS를 사용하는 경우 실제로 요청(get, post)을 보내기 이전에 OPTIONS 메소드로 사전 검사 요청 => 전부 통과(pass)하도록 설정
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 정적 리소스(html, js, png 등)를 요청하는 경우 토큰 검증과 관련 없음 => 전부 통과(pass)하도록 설정
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // HTTP 요청 헤더에서 x-user-** 추출하고 RequestAttributes 객체에 저장
        String userId = request.getHeader("x-user-id");
        if (userId == null) {
            throw new ApiException(UserErrorCode.X_USER_ID_NOT_FOUND);
        }
        String name = request.getHeader("x-user-name");
        String email = request.getHeader("x-user-email");
        String status = request.getHeader("x-user-status");
        String address = request.getHeader("x-user-address");

        // RequestContextHolder => HTTP 요청을 처리하면서 발생하는 데이터와 상태를 스레드 로컬(ThreadLocal)에 저장하고 검색, 전역적으로 공유
        // 현재 들어온 요청과 관련된 RequestAttributes 객체(requestContext)
        RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());

        // RequestAttributes 객체(requestContext)에 데이터 저장
        // 이 데이터의 범위(scope)를 현재 요청(request)으로 지정 즉, 데이터가 현재 HTTP 요청 범위 내에서만 유효
        // 여러 컴포넌트에서 데이터를 공유하고 활용 가능
        requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
        requestContext.setAttribute("name", fromUnicode(name), RequestAttributes.SCOPE_REQUEST);
        requestContext.setAttribute("email", fromUnicode(email), RequestAttributes.SCOPE_REQUEST);
        requestContext.setAttribute("status", status, RequestAttributes.SCOPE_REQUEST);
        requestContext.setAttribute("address", fromUnicode(address), RequestAttributes.SCOPE_REQUEST);

        return true;
    }

    /**
     * Unicode 이스케이프 시퀀스를 실제 문자열로 변환하는 메서드
     * @param unicodeStr Unicode 이스케이프 시퀀스를 포함한 문자열
     * @return 변환된 실제 문자열
     */
    private String fromUnicode(String unicodeStr) {
        StringBuilder string = new StringBuilder();
        int i = 0;
        while (i < unicodeStr.length()) {
            // '\\'로 시작하고 그 다음 문자가 'u'인 경우
            if (unicodeStr.charAt(i) == '\\' && unicodeStr.charAt(i + 1) == 'u') {
                // 이스케이프 시퀀스를 실제 문자로 변환하고 추가
                char character = (char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16);
                string.append(character);
                // 다음 이스케이프 시퀀스로 이동
                i += 6;
            } else {
                // 이스케이프 시퀀스가 아니라면 그대로 추가
                string.append(unicodeStr.charAt(i));
                // 다음 문자로 이동
                i++;
            }
        }
        // 변환된 문자열 반환
        return string.toString();
    }
}
