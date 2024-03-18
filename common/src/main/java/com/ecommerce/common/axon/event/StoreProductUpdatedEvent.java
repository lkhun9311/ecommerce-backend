package com.ecommerce.common.axon.event;

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
    private Long storeId;
    private String name;
    private BigDecimal amount;
    private String thumbnailUrl;
}
