package com.ecommerce.product.domain.storeproduct.controller;

import com.ecommerce.product.common.annotation.UserSession;
import com.ecommerce.product.common.api.Api;
import com.ecommerce.product.domain.storeproduct.business.StoreProductBusiness;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductRegisterRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductResponse;
import com.ecommerce.product.resolver.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-product")
public class StoreProductApiController {

    private final StoreProductBusiness storeProductBusiness;

    /**
     * 새로운 상품을 등록하는 API 엔드포인트
     *
     * @param request 등록할 상품 정보를 담은 요청 객체
     * @return 등록된 상품 정보를 담은 응답 객체
     */
    @PostMapping("/register")
    public Api<StoreProductResponse> register(
            @Valid
            @RequestBody
            Api<StoreProductRegisterRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreProductRegisterRequest requestBody = request.getBody();
        StoreProductResponse response = storeProductBusiness.register(requestBody, user);
        return Api.ok(response);
    }
}
