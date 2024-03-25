package com.ecommerce.store.axon.command;

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
public class DeleteStoreCommand {
    @TargetAggregateIdentifier
    private String storeId;
    private LocalDateTime unregisteredAt;
}
