package com.ecommerce.store.axon.command;

import com.ecommerce.common.model.enums.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CreateStoreCommand {
    @TargetAggregateIdentifier
    private String storeId;
    private String name;
    private String address;
    private StoreCategory category;
    private String thumbnailUrl;
    private String phoneNumber;
    private Boolean isDoubleChecked;
    private LocalDateTime registeredAt;
}
