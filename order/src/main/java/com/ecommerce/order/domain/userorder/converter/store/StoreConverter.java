package com.ecommerce.order.domain.userorder.converter.store;

import com.ecommerce.order.common.annotation.Converter;

@Converter
public class StoreConverter {
//
//    /**
//     * 상점 엔터티를 상점 응답 객체로 변환하는 메소드
//     *
//     * @param entity 상점 엔터티(StoreEntity)
//     * @return 상점 응답 객체(StoreResponse)
//     * @throws ApiException 엔터티가 null인 경우 발생하는 예외
//     */
//    public StoreResponse toResponse(StoreEntity entity) {
//        return Optional.ofNullable(entity)
//                .map(it -> StoreResponse.builder()
//                        .id(it.getId())
//                        .name(it.getName())
//                        .address(it.getAddress())
//                        .status(it.getStatus())
//                        .category(it.getCategory())
//                        .star(it.getStar())
//                        .thumbnailUrl(it.getThumbnailUrl())
//                        .phoneNumber(it.getPhoneNumber())
//                        .build()
//                )
//                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
//    }
}
