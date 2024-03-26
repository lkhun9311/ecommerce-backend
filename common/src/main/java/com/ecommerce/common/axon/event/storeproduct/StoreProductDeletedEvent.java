package com.ecommerce.common.axon.event.storeproduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StoreProductDeletedEvent {
    private String storeProductId;
}
