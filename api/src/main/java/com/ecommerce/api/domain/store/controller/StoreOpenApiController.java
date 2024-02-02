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
 * 인증이 필요 없는 엔드포인트
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store")
public class StoreOpenApiController {

    private final StoreBusiness storeBusiness;

    /**
     * 상점 등록 엔드포인트
     *
     * @param request 상점 등록 요청(Api<StoreRegisterRequest>)
     * @return 상점 등록 결과 응답(Api<StoreResponse>)
     */
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
