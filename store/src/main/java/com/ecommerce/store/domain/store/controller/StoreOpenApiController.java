package com.ecommerce.store.domain.store.controller;

import com.ecommerce.store.common.api.Api;
import com.ecommerce.store.domain.store.business.StoreBusiness;
import com.ecommerce.store.domain.store.controller.model.StoreResponse;
import com.ecommerce.common.model.enums.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 특정 카테고리의 상점 목록을 조회
     *
     * @param category 조회할 상점 카테고리
     * @return 조회된 상점 목록을 담은 API 객체
     */
    @GetMapping("/search/category")
    public Api<List<StoreResponse>> searchByCategory(
            @RequestParam(name = "category")
            StoreCategory category
    ) {
        List<StoreResponse> response = storeBusiness.searchByCategory(category);
        return Api.ok(response);
    }

    /**
     * 상점 ID를 기준으로 상점 조회
     *
     * @param storeId 조회할 상점의 식별자
     * @return 조회된 상점 정보를 담은 API 객체
     */
    @GetMapping("/search/store-id")
    public Api<StoreResponse> searchByStoreId(
            @RequestParam(name = "store-id")
            String storeId
    ) {
        StoreResponse response = storeBusiness.searchByStoreId(storeId);
        return Api.ok(response);
    }

    /**
     * 모든 상점 조회
     *
     * @return 조회된 모든 상점 목록을 담은 API 객체
     */
    @GetMapping("/search/all")
    public Api<List<StoreResponse>> searchAll() {
        List<StoreResponse> response = storeBusiness.searchAll();
        return Api.ok(response);
    }

}
