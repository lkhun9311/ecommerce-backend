package com.ecommerce.store.domain.store.controller.model;

import com.ecommerce.store.entity.enums.StoreCategory;
import com.ecommerce.store.entity.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {

    private Long id;

    private String name;

    private String address;

    private StoreStatus status;

    private StoreCategory category;

    private double star;

    private String thumbnailUrl;

    private String phoneNumber;
}
