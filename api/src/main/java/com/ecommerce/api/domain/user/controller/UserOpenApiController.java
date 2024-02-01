package com.ecommerce.api.domain.user.controller;

import com.ecommerce.api.common.api.Api;
import com.ecommerce.api.domain.token.controller.model.TokenResponse;
import com.ecommerce.api.domain.user.business.UserBusiness;
import com.ecommerce.api.domain.user.controller.model.UserLoginRequest;
import com.ecommerce.api.domain.user.controller.model.UserRegisterRequest;
import com.ecommerce.api.domain.user.controller.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 외부에 공개된 User API 컨트롤러
 * 회원가입, 로그인 API 제공
 * 인증이 필요 없는 엔드포인트
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    /**
     * 회원가입 API
     * @param request 회원가입 요청 데이터
     * @return 회원가입 응답 데이터
     */
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody
            Api<UserRegisterRequest> request
    ) {
        UserResponse response = userBusiness.register(request.getBody());
        return Api.ok(response);
    }

    /**
     * 로그인 API
     * @param request 로그인 요청 데이터
     * @return 로그인 응답 데이터 (JWT Token)
     */
    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid
            @RequestBody
            Api<UserLoginRequest> request
    ) {
        TokenResponse response = userBusiness.login(request.getBody());
        return Api.ok(response);
    }

}
