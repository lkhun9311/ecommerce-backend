package com.ecommerce.api.domain.storeproduct.business;

import com.ecommerce.api.common.annotation.Business;
import com.ecommerce.api.domain.storeproduct.controller.model.StoreProductRegisterRequest;
import com.ecommerce.api.domain.storeproduct.controller.model.StoreProductResponse;
import com.ecommerce.api.domain.storeproduct.converter.StoreProductConverter;
import com.ecommerce.api.domain.storeproduct.service.StoreProductService;
import com.ecommerce.db.storeproduct.StoreProductEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreProductBusiness {

    private final StoreProductService storeProductService;
    private final StoreProductConverter storeProductConverter;

    /**
     * 주어진 요청을 처리해 상품을 등록하는 메소드
     *
     * @param request 등록할 상품의 정보를 담은 요청 객체
     * @return 등록된 상품 정보를 담은 응답 객체
     */
    public StoreProductResponse register(StoreProductRegisterRequest request) {
        StoreProductEntity entity = storeProductConverter.toEntity(request);
        StoreProductEntity newEntity = storeProductService.register(entity);
        return storeProductConverter.toResponse(newEntity);
    }

    /**
     * 지정된 상점 ID에 해당하는 상품 리스트를 검색하는 메소드
     *
     * @param storeId 검색할 상품이 속한 상점의 ID
     * @return 검색된 상품 정보를 담은 응답 객체 리스트
     */
    public List<StoreProductResponse> search(Long storeId) {
        List<StoreProductEntity> storeProductList = storeProductService.getStoreProductByStoreId(storeId);

        return storeProductList.stream()
                .map(storeProductConverter::toResponse)
                .collect(Collectors.toList());
    }
}
