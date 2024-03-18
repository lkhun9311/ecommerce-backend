package com.ecommerce.order.domain.userorder.converter.storeproduct;

import com.ecommerce.order.common.annotation.Converter;

@Converter
public class StoreProductConverter {

//    /**
//     * StoreProductEntity 객체를 StoreProductResponse로 변환하는 메소드
//     *
//     * @param storeProductEntity 변환할 상품 엔티티 객체
//     * @return StoreProductResponse로 변환된 객체
//     * @throws ApiException NULL_POINT_ERROR: 상품 엔티티 객체가 null일 경우
//     */
//    public StoreProductResponse toResponse(StoreProductEntity storeProductEntity) {
//        return Optional.ofNullable(storeProductEntity)
//                .map(it -> StoreProductResponse.builder()
//                        .id(it.getId())
//                        .storeId(it.getStoreId())
//                        .name(it.getName())
//                        .amount(it.getAmount())
//                        .status(it.getStatus())
//                        .thumbnailUrl(it.getThumbnailUrl())
//                        .likeCount(it.getLikeCount())
//                        .build()
//                )
//                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
//    }
//
//    /**
//     * StoreProductEntity 리스트를 StoreProductResponse 리스트로 변환하는 메소드
//     *
//     * @param list StoreProductEntity 리스트
//     * @return StoreProductResponse 리스트
//     */
//    public List<StoreProductResponse> toRespnonse(List<StoreProductEntity> list) {
//        return list.stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//    }
}
