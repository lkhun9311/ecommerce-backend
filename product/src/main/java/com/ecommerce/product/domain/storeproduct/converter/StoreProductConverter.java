package com.ecommerce.product.domain.storeproduct.converter;

import com.ecommerce.db.storeproduct.StoreProductEntity;
import com.ecommerce.product.common.annotation.Converter;
import com.ecommerce.product.common.error.ErrorCode;
import com.ecommerce.product.common.exception.ApiException;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductRegisterRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class StoreProductConverter {

    /**
     * StoreProductRegisterRequest 객체를 StoreProductEntity로 변환하는 메소드
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
     * StoreProductEntity 객체를 StoreProductResponse로 변환하는 메소드
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
                        .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * StoreProductEntity 리스트를 StoreProductResponse 리스트로 변환하는 메소드
     *
     * @param list StoreProductEntity 리스트
     * @return StoreProductResponse 리스트
     */
    public List<StoreProductResponse> toRespnonse(List<StoreProductEntity> list) {
        return list.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
