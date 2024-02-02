package com.ecommerce.api.domain.storeproduct.controller;

import com.ecommerce.api.common.api.Api;
import com.ecommerce.api.domain.storeproduct.business.StoreProductBusiness;
import com.ecommerce.api.domain.storeproduct.controller.model.StoreProductRegisterRequest;
import com.ecommerce.api.domain.storeproduct.controller.model.StoreProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-product")
public class StoreProductOpenApiController {

    private final StoreProductBusiness storeProductBusiness;

    /**
     * 새로운 상품을 등록하는 API 엔드포인트
     *
     * @param request 등록할 상품 정보를 담은 요청 객체
     * @return 등록된 상품 정보를 담은 응답 객체
     */
    @PostMapping("/register")
    public Api<StoreProductResponse> register(
            @Valid @RequestBody Api<StoreProductRegisterRequest> request
    ) {
        StoreProductRegisterRequest requestBody = request.getBody();
        StoreProductResponse response = storeProductBusiness.register(requestBody);
        return Api.ok(response);
    }
}
