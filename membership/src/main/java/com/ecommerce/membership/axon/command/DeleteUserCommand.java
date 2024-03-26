package com.ecommerce.membership.axon.command;

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
public class DeleteUserCommand {
    @TargetAggregateIdentifier
    private String userId;
    private LocalDateTime unregisteredAt;
}
