package com.ecommerce.api.domain.store.controller;

import com.ecommerce.api.common.api.Api;
import com.ecommerce.api.domain.store.business.StoreBusiness;
import com.ecommerce.api.domain.store.controller.model.StoreResponse;
import com.ecommerce.db.store.enums.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {

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
