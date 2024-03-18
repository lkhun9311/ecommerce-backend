package com.ecommerce.product.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UpdateStoreProductCommand {
    @TargetAggregateIdentifier
    private String storeProductId;
    private Long storeId;
    private String name;
    private BigDecimal amount;
    private String thumbnailUrl;
}
