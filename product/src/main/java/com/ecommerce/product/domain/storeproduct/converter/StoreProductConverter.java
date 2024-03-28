package com.ecommerce.product.domain.storeproduct.converter;

import com.ecommerce.common.axon.event.storeproduct.StoreProductCreatedEvent;
import com.ecommerce.common.model.storeproduct.StoreProductQueryResponse;
import com.ecommerce.product.common.annotation.Converter;
import com.ecommerce.product.common.error.ErrorCode;
import com.ecommerce.product.common.exception.ApiException;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductResponse;
import com.ecommerce.product.entity.StoreProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class StoreProductConverter {

    /**
     * StoreProductRegisterRequest 객체를 StoreProductEntity로 변환하는 메소드
     *
     * @param event 등록할 상품 정보를 담은 이벤트 객체
     * @return StoreProductEntity로 변환된 객체
     * @throws ApiException NULL_POINT_ERROR: 요청 객체가 null일 경우
     */
    public StoreProductEntity toEntity(StoreProductCreatedEvent event) {
        return Optional.ofNullable(event)
                .map(it -> StoreProductEntity.builder()
                        .storeProductId(it.getStoreProductId())
                        .storeId(it.getStoreId())
                        .name(it.getName())
                        .amount(it.getAmount())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .color(it.getColor())
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
                        .id(it.getStoreProductId())
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
    public List<StoreProductResponse> toResponse(List<StoreProductEntity> list) {
        return list.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * StoreProductEntity 객체를 StoreProductQueryResponse로 변환하는 메소드
     *
     * @param storeProductEntity 변환할 상품 엔티티 객체
     * @return StoreProductQueryResponse로 변환된 객체
     * @throws ApiException NULL_POINT_ERROR: 상품 엔티티 객체가 null일 경우
     */
    public StoreProductQueryResponse toQueryResponse(StoreProductEntity storeProductEntity) {
        return Optional.ofNullable(storeProductEntity)
                .map(it -> StoreProductQueryResponse.builder()
                        .id(it.getStoreProductId())
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
     * StoreProductEntity 리스트를 StoreProductQueryResponse 리스트로 변환하는 메소드
     *
     * @param list StoreProductEntity 리스트
     * @return StoreProductQueryResponse 리스트의 CompletableFuture
     */
    public List<StoreProductQueryResponse> toQueryResponse(List<StoreProductEntity> list) {
        return list.stream()
                .map(this::toQueryResponse)
                .collect(Collectors.toList());
    }
}
