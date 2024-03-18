package com.ecommerce.order.domain.userorder.converter.userorder;

import com.ecommerce.order.common.annotation.Converter;

@Converter
public class UserOrderConverter {

//    public UserOrderEntity toEntity(
//            User user,
//            Long storeId,
//            List<StoreProductEntity> storeProductEntityList
//    ) {
//        BigDecimal totalAmount = storeProductEntityList.stream()
//                .map(StoreProductEntity::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add); // reduce 함수를 사용해 모든 BigDecimal 합산
//                                                           // 초기값은 BigDecimal.ZERO, 각 BigDecimal을 누적해 더함
//
//        return UserOrderEntity.builder()
//                .userId(user.getId())
//                .storeId(storeId)
//                .amount(totalAmount)
//                .build();
//    }
//
//    public UserOrderResponse toResponse(UserOrderEntity entity) {
//        return UserOrderResponse.builder()
//                .id(entity.getId())
//                .status(entity.getStatus())
//                .amount(entity.getAmount())
//                .orderedAt(entity.getOrderedAt())
//                .acceptedAt(entity.getAcceptedAt())
//                .deliveryStartedAt(entity.getDeliveryStartedAt())
//                .receivedAt(entity.getReceivedAt())
//                .build();
//    }
}
