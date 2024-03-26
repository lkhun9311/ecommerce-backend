package com.ecommerce.common.axon.event.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StoreDeletedEvent {
    private String storeId;
    private LocalDateTime unregisteredAt;
}
