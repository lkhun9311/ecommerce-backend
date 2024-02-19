package com.ecommerce.store.domain.store.controller;

import com.ecommerce.db.store.enums.StoreCategory;
import com.ecommerce.store.common.api.Api;
import com.ecommerce.store.domain.store.business.StoreBusiness;
import com.ecommerce.store.domain.store.controller.model.StoreResponse;
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
     * 특정 카테고리의 상점 목록을 검색하는 엔드포인트
     *
     * @param category 검색할 상점 카테고리 (선택 사항)
     * @return 상점 목록 응답(Api<List<StoreResponse>>)
     */
    @GetMapping("/search")
    public Api<List<StoreResponse>> search(
            @RequestParam(name = "category", required = false)
            StoreCategory category
    ) {
        List<StoreResponse> response = storeBusiness.searchCategory(category);
        return Api.ok(response);
    }
}
