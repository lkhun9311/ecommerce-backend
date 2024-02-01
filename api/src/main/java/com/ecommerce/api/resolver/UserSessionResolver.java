package com.ecommerce.api.resolver;

import com.ecommerce.api.common.annotation.UserSession;
import com.ecommerce.api.common.error.UserErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.user.model.User;
import com.ecommerce.api.domain.user.service.UserService;
import com.ecommerce.db.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 요청이 들어오면 실행
// AOP 방식으로 동작
@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    // 지원하는 파라미터와 어노테이션 체크
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // 1. UserSession 어노테이션이 있는지 체크
        Boolean annotation = parameter.hasParameterAnnotation(UserSession.class);

        // 2. 파라미터 타입이 User 타입인지 체크
        Boolean parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType);
    }

    // supportsParameter 메소드에서 true 반환 시 resolveArgument 메소드 실행
    // 현재 요청의 userId 가져 옴 => UserService를 통해 해당 사용자의 UserEntity 가져 옴 => 이를 기반으로 User 객체 생성 => return User 객체
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        if (requestContext != null) {
            Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

            if (userId != null) {
                // UserService와 userId를 통해 userEntity 반환
                UserEntity userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

                // User(사용자) 정보 셋팅
                return User.builder()
                        .id(userEntity.getId())
                        .name(userEntity.getName())
                        .email(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .status(userEntity.getStatus())
                        .address(userEntity.getAddress())
                        .registeredAt(userEntity.getRegisteredAt())
                        .unregisteredAt(userEntity.getUnregisteredAt())
                        .lastLoginAt(userEntity.getLastLoginAt())
                        .build();
            } else {
                // userId가 null
                throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
            }
        } else {
            // requestContext가 null
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }
    }
}
