package com.ecommerce.product.domain.storeproduct.service;

import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductCreateRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductDeleteRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductUpdateRequest;
import com.ecommerce.product.entity.StoreProductEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StoreProductService {
    String createStoreProduct(StoreProductCreateRequest request);
    String updateStoreProduct(StoreProductUpdateRequest request);
    String deleteStoreProduct(StoreProductDeleteRequest request);
    List<StoreProductEntity> getStoreProductByStoreId(String storeId);
    StoreProductEntity getStoreProductWithThrow(String storeProductId);
    CompletableFuture<Boolean> getStoreIdChecked(String storeId);
    void resetStoreProduct();
}
