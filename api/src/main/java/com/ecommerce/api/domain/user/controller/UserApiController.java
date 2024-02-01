package com.ecommerce.api.domain.user.controller;

import com.ecommerce.api.common.annotation.UserSession;
import com.ecommerce.api.common.api.Api;
import com.ecommerce.api.domain.user.business.UserBusiness;
import com.ecommerce.api.domain.user.controller.model.UserResponse;
import com.ecommerce.api.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 외부로 부터 차단된 API
// 인증이 필요한 API (사전에 로그인 필요)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    /**
     * 현재 로그인한 사용자의 정보를 조회하는 API
     * @param user 현재 로그인한 사용자 정보를 담고 있는 User 객체, @UserSession 어노테이션을 통해 주입 (메소드 실행 전에 AOP가 동작해 세션 정보 주입)
     * @return 현재 로그인한 사용자의 정보를 담은 응답 객체
     */
    @GetMapping("/me")
    public Api<UserResponse> me(
            @UserSession User user
    ) {
        UserResponse response = userBusiness.me(user);
        return Api.ok(response);
    }
}
