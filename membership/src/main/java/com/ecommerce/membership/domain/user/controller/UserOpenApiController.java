package com.ecommerce.membership.domain.user.controller;

import com.ecommerce.membership.common.api.Api;
import com.ecommerce.membership.domain.token.controller.model.TokenResponse;
import com.ecommerce.membership.domain.user.business.UserBusiness;
import com.ecommerce.membership.domain.user.controller.model.UserCreateRequest;
import com.ecommerce.membership.domain.user.controller.model.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
     * 회원가입
     * @param request 회원가입 요청 데이터
     * @return 회원가입 응답 데이터
     */
    @PostMapping("/create")
    public Api<String> createUser(
            @Valid
            @RequestBody
            Api<UserCreateRequest> request
    ) {
        String response = userBusiness.createUser(request.getBody());
        return Api.ok(response);
    }

    /**
     * 로그인
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

    /**
     * 사용자 이메일 중복 확인
     *
     * @param email 중복 확인할 사용자의 이메일
     * @return 사용자의 이메일 중복 여부를 담은 API 객체
     */
    @GetMapping("/double-check")
    public Api<Boolean> doubleCheckStore(
            @RequestParam(name = "email")
            String email
    ) {
        Boolean response = userBusiness.doubleCheckEmail(email);
        return Api.ok(response);
    }
}
