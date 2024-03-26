package com.ecommerce.common.axon.event.storeproduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StoreProductUpdatedEvent {
    private String storeProductId;
    private String storeId;
    private String name;
    private BigDecimal amount;
    private String thumbnailUrl;
}
