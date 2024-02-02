package com.ecommerce.api.domain.storeproduct.converter;

import com.ecommerce.api.common.annotation.Converter;
import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.storeproduct.controller.model.StoreProductRegisterRequest;
import com.ecommerce.api.domain.storeproduct.controller.model.StoreProductResponse;
import com.ecommerce.db.storeproduct.StoreProductEntity;

import java.util.Optional;

@Converter
public class StoreProductConverter {

    /**
     * StoreProductRegisterRequest 객체를 StoreProductEntity로 변환하는 메서드
     *
     * @param request 등록할 상품 정보를 담은 요청 객체
     * @return StoreProductEntity로 변환된 객체
     * @throws ApiException NULL_POINT_ERROR: 요청 객체가 null일 경우
     */
    public StoreProductEntity toEntity(StoreProductRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> StoreProductEntity.builder()
                        .storeId(it.getStoreId())
                        .name(it.getName())
                        .amount(it.getAmount())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * StoreProductEntity 객체를 StoreProductResponse로 변환하는 메서드
     *
     * @param storeProductEntity 변환할 상품 엔티티 객체
     * @return StoreProductResponse로 변환된 객체
     * @throws ApiException NULL_POINT_ERROR: 상품 엔티티 객체가 null일 경우
     */
    public StoreProductResponse toResponse(StoreProductEntity storeProductEntity) {
        return Optional.ofNullable(storeProductEntity)
                .map(it -> StoreProductResponse.builder()
                        .id(it.getId())
                        .storeId(it.getStoreId())
                        .name(it.getName())
                        .amount(it.getAmount())
                        .status(it.getStatus())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .likeCount(it.getLikeCount())
                        .sequence(it.getSequence())
                        .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }
}
