package com.ecommerce.storeadmin.domain.user.controller;

import com.ecommerce.storeadmin.common.api.Api;
import com.ecommerce.storeadmin.domain.authorization.model.UserSession;
import com.ecommerce.storeadmin.domain.user.controller.model.StoreUserResponse;
import com.ecommerce.storeadmin.domain.user.converter.StoreUserConverter;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-user")
public class StoreUserApiController {

    private final StoreUserConverter storeUserConverter;

    /**
     * 현재 인증된 사용자에 대한 정보를 조회
     * @param userSession 현재 인증된 사용자의 세션 정보를 나타내는 객체
     *                    사용자의 식별자, 이메일, 권한 등의 정보 포함
     *                    AuthenticationPrincipal 어노테이션을 통해 주입
     * @return Api<StoreUserResponse> 객체(현재 사용자의 정보를 담은 응답)
     */
    @GetMapping("/me")
    public Api<StoreUserResponse> me(
            @Parameter(hidden = true)
            @AuthenticationPrincipal
            UserSession userSession
    ){
        StoreUserResponse response = storeUserConverter.toResponse(userSession);
        return Api.ok(response);
    }
}
