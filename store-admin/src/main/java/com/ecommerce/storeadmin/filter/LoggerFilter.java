package com.ecommerce.storeadmin.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(httpResponse);

        // 요청이 Server Sent Events인지 확인
        boolean isSseRequest = isSseRequest(httpRequest);

        // Server-Sent Events 요청이 아닌 경우에만 응답 본문을 캐싱하도록 체인 호출
        if (isSseRequest) {
            chain.doFilter(req, response);
        } else {
            chain.doFilter(req, res);
        }

        // 요청 및 응답 정보 로깅
        logRequestInfo(req);
        logResponseInfo(httpRequest, res);

        // 응답 본문을 복사해 재전송
        res.copyBodyToResponse();
    }

    // 요청이 Server Sent Events인지 확인하는 메서드
    private boolean isSseRequest(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        return acceptHeader != null && acceptHeader.contains("text/event-stream");
    }

    // 요청 정보를 로깅하는 메서드
    private void logRequestInfo(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            String headerValue = request.getHeader(headerKey);
            headerValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        // 요청 본문을 문자열로 변환
        String requestBody = new String(((ContentCachingRequestWrapper) request).getContentAsByteArray());
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 로깅
        log.info(">>> uri : {}, method : {}, header : {}, body : {}", uri, method, headerValues, requestBody);
    }

    // 응답 정보를 로깅하는 메서드
    private void logResponseInfo(HttpServletRequest request, ContentCachingResponseWrapper response) {
        StringBuilder responseHeaderValues = new StringBuilder();

        response.getHeaderNames().forEach(headerKey -> {
            String headerValue = response.getHeader(headerKey);
            responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue).append("] ");
        });

        // 응답 본문을 문자열로 변환
        String responseBody = new String(response.getContentAsByteArray());
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 로깅
        log.info("<<< uri : {}, method : {}, header : {}, body : {}", uri, method, responseHeaderValues, responseBody);
    }
}
