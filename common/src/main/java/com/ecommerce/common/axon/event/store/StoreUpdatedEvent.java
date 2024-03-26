package com.ecommerce.common.axon.event.store;

import com.ecommerce.common.model.enums.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class StoreUpdatedEvent {
    private String storeId;
    private String name;
    private String address;
    private StoreCategory category;
    private double star;
    private String thumbnailUrl;
    private String phoneNumber;
    private Boolean isDoubleChecked;
    private LocalDateTime updatedAt;
}
