package com.ecommerce.common.axon.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.serialization.Revision;

import java.math.BigDecimal;

@Revision("1.0")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StoreProductCreatedEvent {
    private String storeProductId;
    private Long storeId;
    private String name;
    private BigDecimal amount;
    private String thumbnailUrl;
    private String color;
}
