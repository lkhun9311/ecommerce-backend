package com.ecommerce.api.domain.userorderproduct.converter;

import com.ecommerce.api.common.annotation.Converter;
import com.ecommerce.db.storeproduct.StoreProductEntity;
import com.ecommerce.db.userorder.UserOrderEntity;
import com.ecommerce.db.userorderproduct.UserOrderProductEntity;

@Converter
public class UserOrderProductConverter {

    public UserOrderProductEntity toEntity(
            UserOrderEntity userOrderEntity,
            StoreProductEntity storeProductEntity
    ) {
        return UserOrderProductEntity.builder()
                .userOrderId(userOrderEntity.getId())
                .storeProductId(storeProductEntity.getId())
                .build();
    }
}
