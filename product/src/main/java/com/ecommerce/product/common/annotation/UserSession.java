package com.ecommerce.product.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 사용자 세션을 나타내는 커스텀 어노테이션
@Target(ElementType.PARAMETER) // @UserSession이 적용될 대상 지정 => 메소드의 파라미터로 지정
@Retention(RetentionPolicy.RUNTIME) // 어노테이션이 어느 시점까지 유지될 지 지정 => 어노테이션 정보를 런타임에 프로그램에서 읽을 수 있음
public @interface UserSession {
}
