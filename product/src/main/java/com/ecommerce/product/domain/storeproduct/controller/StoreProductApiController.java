package com.ecommerce.product.domain.storeproduct.controller;

import com.ecommerce.common.model.user.User;
import com.ecommerce.product.common.annotation.UserSession;
import com.ecommerce.product.common.api.Api;
import com.ecommerce.product.domain.storeproduct.business.StoreProductBusiness;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductCreateRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductDeleteRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductUpdateRequest;
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
     * 상품 생성
     *
     * @param request 상품 생성 요청을 담은 API 객체
     * @param user 사용자 세션 정보
     * @return 상품 생성 결과를 담은 API 객체
     */
    @PostMapping("/create")
    public Api<String> createStoreProduct(
            @Valid
            @RequestBody
            Api<StoreProductCreateRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreProductCreateRequest body = request.getBody();
        String response = storeProductBusiness.createStoreProduct(body, user);
        return Api.ok(response);
    }

    /**
     * 상품 수정
     *
     * @param request 상품 수정 요청을 담은 API 객체
     * @param user 사용자 세션 정보
     * @return 상품 수정 결과를 담은 API 객체
     */
    @PutMapping("/update")
    public Api<String> updateStoreProduct(
            @Valid
            @RequestBody
            Api<StoreProductUpdateRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreProductUpdateRequest body = request.getBody();
        String response = storeProductBusiness.updateStoreProduct(body, user);
        return Api.ok(response);
    }

    /**
     * 상품 삭제
     *
     * @param request 상품 삭제 요청을 담은 API 객체
     * @param user 사용자 세션 정보
     * @return 상품 삭제 결과를 담은 API 객체
     */
    @DeleteMapping("/delete")
    public Api<String> deleteStoreProduct(
            @Valid
            @RequestBody
            Api<StoreProductDeleteRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreProductDeleteRequest body = request.getBody();
        String response = storeProductBusiness.deleteStoreProduct(body, user);
        return Api.ok(response);
    }

    /**
     * 상품 모델 재설정
     *
     * @param user 사용자 세션 정보
     */
    @PostMapping("/reset")
    public void reset(
            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        storeProductBusiness.resetStoreProduct();
    }
}
