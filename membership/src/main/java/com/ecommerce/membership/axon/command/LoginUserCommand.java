package com.ecommerce.membership.axon.command;

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
public class LoginUserCommand {
    @TargetAggregateIdentifier
    private String userId;
    private LocalDateTime lastLoginAt;
}
