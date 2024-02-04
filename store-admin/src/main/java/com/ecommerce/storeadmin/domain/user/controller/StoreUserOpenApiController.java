package com.ecommerce.storeadmin.domain.user.controller;

import com.ecommerce.storeadmin.common.api.Api;
import com.ecommerce.storeadmin.domain.user.busniess.StoreUserBusiness;
import com.ecommerce.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import com.ecommerce.storeadmin.domain.user.controller.model.StoreUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

    private final StoreUserBusiness storeUserBusiness;

    /**
     * 새로운 상점 사용자 등록
     *
     * @param request 등록할 상점 사용자의 정보를 담고 있는 요청 객체
     * @return 등록된 상점 사용자 정보를 담은 응답 객체
     */
    @PostMapping("/register")
    public Api<StoreUserResponse> register(
            @Valid
            @RequestBody
            StoreUserRegisterRequest request
    ){
        return storeUserBusiness.register(request);
    }
}
