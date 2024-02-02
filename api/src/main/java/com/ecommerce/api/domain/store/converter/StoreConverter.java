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

    public StoreEntity toEntity(StoreRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> StoreEntity.builder()
                        .name(request.getName())
                        .address(request.getAddress())
                        .category(request.getCategory())
                        .thumbnailUrl(request.getThumbnailUrl())
                        .phoneNumber(request.getPhoneNumber())
                        .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    public StoreResponse toResponse(StoreEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> StoreResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .address(entity.getAddress())
                            .status(entity.getStatus())
                            .category(entity.getCategory())
                            .star(entity.getStar())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .phoneNumber(entity.getPhoneNumber())
                            .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }
}
