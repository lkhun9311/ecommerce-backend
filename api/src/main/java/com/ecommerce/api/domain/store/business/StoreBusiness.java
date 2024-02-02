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

    /**
     * 상점 등록 로직
     *
     * @param request 상점 등록 요청 객체(StoreRegisterRequest)
     * @return 상점 등록 결과 응답 객체(StoreResponse)
     */
    public StoreResponse register(StoreRegisterRequest request) {
        StoreEntity entity = storeConverter.toEntity(request);
        StoreEntity newEntity = storeService.register(entity);
        return storeConverter.toResponse(newEntity);
    }

    /**
     * 카테고리별 상점 검색 로직
     *
     * @param category 검색할 상점 카테고리(StoreCategory)
     * @return 검색된 상점 목록 응답 객체 리스트(List<StoreResponse>)
     */
    public List<StoreResponse> searchCategory(StoreCategory category) {
        List<StoreEntity> storeList = storeService.searchByCategory(category);
        return storeList.stream()
                .map(storeConverter::toResponse)
                .collect(Collectors.toList());
    }
}
