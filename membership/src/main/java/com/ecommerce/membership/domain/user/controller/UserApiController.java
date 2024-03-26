package com.ecommerce.membership.domain.user.controller;

import com.ecommerce.membership.common.annotation.UserSession;
import com.ecommerce.membership.common.api.Api;
import com.ecommerce.membership.domain.user.business.UserBusiness;
import com.ecommerce.membership.domain.user.controller.model.UserDeleteRequest;
import com.ecommerce.membership.domain.user.controller.model.UserResponse;
import com.ecommerce.membership.domain.user.controller.model.UserUpdateRequest;
import com.ecommerce.common.model.user.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// 외부로 부터 차단된 API
// 인증이 필요한 API (사전에 로그인 필요)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private final UserBusiness userBusiness;

    /**
     * 사용자 정보 수정
     *
     * @param request 사용자 수정 요청을 담은 API 객체
     * @param user 사용자 세션 정보
     * @return 사용자 수정 결과를 담은 API 객체
     */
    @PutMapping("/update")
    public Api<String> updateUser(
            @Valid
            @RequestBody
            Api<UserUpdateRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        UserUpdateRequest body = request.getBody();
        String response = userBusiness.updateUser(body, user);
        return Api.ok(response);
    }

    /**
     * 사용자 정보 삭제
     *
     * @param request 사용자 삭제 요청을 담은 API 객체
     * @param user 사용자 세션 정보
     * @return 사용자 삭제 결과를 담은 API 객체
     */
    @DeleteMapping("/delete")
    public Api<String> deleteUser(
            @Valid
            @RequestBody
            Api<UserDeleteRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        UserDeleteRequest body = request.getBody();
        String response = userBusiness.deleteUser(body, user);
        return Api.ok(response);
    }

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

    /**
     * 사용자 모델 재설정
     *
     * @param user 사용자 세션 정보
     */
    @PostMapping("/reset")
    public void resetUser(
            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        userBusiness.resetUser();
    }
}
