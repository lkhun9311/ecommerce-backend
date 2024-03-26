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
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String name;
    private String email;
    private String password;
    private String address;
    private String thumbnailUrl;
    private String phoneNumber;
    private Boolean isDoubleChecked;
    private LocalDateTime updatedAt;
}
