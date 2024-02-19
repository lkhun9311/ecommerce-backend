package com.ecommerce.api.domain.userorder.converter;

import com.ecommerce.api.common.annotation.Converter;
import com.ecommerce.api.resolver.model.User;
import com.ecommerce.api.domain.userorder.controller.model.UserOrderResponse;
import com.ecommerce.db.storeproduct.StoreProductEntity;
import com.ecommerce.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {

    public UserOrderEntity toEntity(
            User user,
            Long storeId,
            List<StoreProductEntity> storeProductEntityList
    ) {
        BigDecimal totalAmount = storeProductEntityList.stream()
                .map(StoreProductEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // reduce 함수를 사용해 모든 BigDecimal 합산
                                                           // 초기값은 BigDecimal.ZERO, 각 BigDecimal을 누적해 더함

        return UserOrderEntity.builder()
                .userId(user.getId())
                .storeId(storeId)
                .amount(totalAmount)
                .build();
    }

    public UserOrderResponse toResponse(UserOrderEntity entity) {
        return UserOrderResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .orderedAt(entity.getOrderedAt())
                .acceptedAt(entity.getAcceptedAt())
                .deliveryStartedAt(entity.getDeliveryStartedAt())
                .receivedAt(entity.getReceivedAt())
                .build();
    }
}
