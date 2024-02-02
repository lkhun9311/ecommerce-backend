package com.ecommerce.api.domain.store.controller;

import com.ecommerce.api.common.api.Api;
import com.ecommerce.api.domain.store.business.StoreBusiness;
import com.ecommerce.api.domain.store.controller.model.StoreRegisterRequest;
import com.ecommerce.api.domain.store.controller.model.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 외부에 공개된 Store API 컨트롤러
 * ?회원가입, 로그인 API 제공?
 * 인증이 필요 없는 엔드포인트
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store")
public class StoreOpenApiController {

    private final StoreBusiness storeBusiness;

    @PostMapping("/register")
    public Api<StoreResponse> register(
            @Valid
            @RequestBody
            Api<StoreRegisterRequest> request
    ) {
        StoreResponse response = storeBusiness.register(request.getBody());
        return Api.ok(response);
    }
}
