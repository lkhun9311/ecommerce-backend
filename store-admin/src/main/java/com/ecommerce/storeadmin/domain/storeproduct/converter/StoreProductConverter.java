package com.ecommerce.storeadmin.domain.storeproduct.converter;

import com.ecommerce.db.storeproduct.StoreProductEntity;
import com.ecommerce.storeadmin.common.annotation.Converter;
import com.ecommerce.storeadmin.domain.storeproduct.controller.model.StoreProductResponse;

import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StoreProductConverter {

    public StoreProductResponse toResponse(StoreProductEntity storeProductEntity){

        return StoreProductResponse.builder()
                .id(storeProductEntity.getId())
                .name(storeProductEntity.getName())
                .amount(storeProductEntity.getAmount())
                .status(storeProductEntity.getStatus())
                .thumbnailUrl(storeProductEntity.getThumbnailUrl())
                .likeCount(storeProductEntity.getLikeCount())
                .build();
    }

    public List<StoreProductResponse> toResponse(List<StoreProductEntity> list){

        return list.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
