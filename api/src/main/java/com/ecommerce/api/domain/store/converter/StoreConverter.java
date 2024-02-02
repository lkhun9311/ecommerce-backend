package com.ecommerce.api.domain.store.converter;

import com.ecommerce.api.common.annotation.Converter;
import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.store.controller.model.StoreRegisterRequest;
import com.ecommerce.api.domain.store.controller.model.StoreResponse;
import com.ecommerce.db.store.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {

    /**
     * 상점 등록 요청 객체를 상점 엔터티로 변환하는 메소드
     *
     * @param request 상점 등록 요청 객체(StoreRegisterRequest)
     * @return 상점 엔터티(StoreEntity)
     * @throws ApiException 요청 객체가 null인 경우 발생하는 예외
     */
    public StoreEntity toEntity(StoreRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> StoreEntity.builder()
                        .name(it.getName())
                        .address(it.getAddress())
                        .category(it.getCategory())
                        .thumbnailUrl(it.getThumbnailUrl())
                        .phoneNumber(it.getPhoneNumber())
                        .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 상점 엔터티를 상점 응답 객체로 변환하는 메소드
     *
     * @param entity 상점 엔터티(StoreEntity)
     * @return 상점 응답 객체(StoreResponse)
     * @throws ApiException 엔터티가 null인 경우 발생하는 예외
     */
    public StoreResponse toResponse(StoreEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> StoreResponse.builder()
                        .id(it.getId())
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
