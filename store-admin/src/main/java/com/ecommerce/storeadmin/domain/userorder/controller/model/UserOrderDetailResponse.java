package com.ecommerce.storeadmin.domain.userorder.controller.model;

import com.ecommerce.storeadmin.domain.storeproduct.controller.model.StoreProductResponse;
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

    private UserOrderResponse userOrderResponse;

    private List<StoreProductResponse> storeProductResponseList;
}
