package com.ecommerce.api.domain.userorder.controller.model;

import com.ecommerce.api.domain.store.controller.model.StoreResponse;
import com.ecommerce.api.domain.storeproduct.controller.model.StoreProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    // 사용자 주문 내역 정보
    private UserOrderResponse userOrderResponse;

    // 주문한 상점 정보
    private StoreResponse storeResponse;

    // 주문한 상품 정보
    private List<StoreProductResponse> storeProductResponseList;
}
