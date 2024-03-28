package com.ecommerce.product.domain.storeproduct.controller;

import com.ecommerce.product.common.api.Api;
import com.ecommerce.product.domain.storeproduct.business.StoreProductBusiness;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-product")
public class StoreProductOpenApiController {

    private final StoreProductBusiness storeProductBusiness;

    /**
     * 지정된 상점 ID에 해당하는 상품 리스트를 검색하는 API 엔드포인트
     *
     * @param storeId 검색할 상품이 속한 상점의 ID
     * @return 검색된 상품 정보를 담은 응답 객체 리스트
     */
    @GetMapping("/search")
    public Api<List<StoreProductResponse>> search(
            @RequestParam String storeId
    ) {
        List<StoreProductResponse> response = storeProductBusiness.search(storeId);
        return Api.ok(response);
    }
}
