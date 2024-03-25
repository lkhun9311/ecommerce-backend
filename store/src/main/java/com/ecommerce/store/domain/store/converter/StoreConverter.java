package com.ecommerce.store.domain.store.converter;

import com.ecommerce.common.axon.event.store.StoreCreatedEvent;
import com.ecommerce.store.common.annotation.Converter;
import com.ecommerce.store.common.error.ErrorCode;
import com.ecommerce.store.common.exception.ApiException;
import com.ecommerce.store.domain.store.controller.model.StoreResponse;
import com.ecommerce.store.entity.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {

    /**
     * 상점 등록 요청 객체를 상점 엔터티로 변환
     *
     * @param event 등록할 상점 정보를 담은 이벤트 객체
     * @return 상점 엔터티(StoreEntity)
     * @throws ApiException 요청 객체가 null인 경우 발생하는 예외
     */
    public StoreEntity toEntity(StoreCreatedEvent event) {
        return Optional.ofNullable(event)
                .map(it -> StoreEntity.builder()
                        .storeId(it.getStoreId())
                        .name(it.getName())
                        .address(it.getAddress())
                        .category(it.getCategory())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .phoneNumber(it.getPhoneNumber())
                        .isDoubleChecked(it.getIsDoubleChecked())
                        .registeredAt(it.getRegisteredAt())
                        .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 상점 엔터티를 상점 응답 객체로 변환
     *
     * @param entity 상점 엔터티(StoreEntity)
     * @return 상점 응답 객체(StoreResponse)
     * @throws ApiException 엔터티가 null인 경우 발생하는 예외
     */
    public StoreResponse toResponse(StoreEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> StoreResponse.builder()
                        .id(it.getStoreId())
                        .name(it.getName())
                        .address(it.getAddress())
                        .status(it.getStatus())
                        .category(it.getCategory())
                        .star(it.getStar())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .phoneNumber(it.getPhoneNumber())
                        .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

}
