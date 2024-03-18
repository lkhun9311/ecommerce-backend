package com.ecommerce.order.domain.userorder.service;

import com.ecommerce.common.model.StoreProductQueryResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StoreProductQueryService {
    CompletableFuture<List<StoreProductQueryResponse>> getStoreProductInfo(List<String> storeProductIdList);
}