package com.ecommerce.api.domain.store.business;

import com.ecommerce.api.common.annotation.Business;
import com.ecommerce.api.domain.store.controller.model.StoreRegisterRequest;
import com.ecommerce.api.domain.store.controller.model.StoreResponse;
import com.ecommerce.api.domain.store.converter.StoreConverter;
import com.ecommerce.api.domain.store.service.StoreService;
import com.ecommerce.db.store.StoreEntity;
import com.ecommerce.db.store.enums.StoreCategory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(StoreRegisterRequest request) {
        StoreEntity entity = storeConverter.toEntity(request);
        StoreEntity newEntity = storeService.register(entity);
        return storeConverter.toResponse(newEntity);
    }
    public List<StoreResponse> searchCategory(StoreCategory category) {
        List<StoreEntity> storeList = storeService.searchByCategory(category);
        return storeList.stream()
                .map(storeConverter::toResponse)
                .collect(Collectors.toList());
    }
}
