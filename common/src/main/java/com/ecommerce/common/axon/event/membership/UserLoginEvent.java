package com.ecommerce.common.axon.event.membership;

import com.ecommerce.common.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserLoginEvent {
    private String userId;
    private LocalDateTime lastLoginAt;
}
