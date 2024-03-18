package com.ecommerce.product.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class DeleteStoreProductCommand {
    @TargetAggregateIdentifier
    private String storeProductId;
}
