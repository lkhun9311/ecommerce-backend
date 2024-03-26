package com.ecommerce.common.axon.event.membership;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserDeletedEvent {
    private String userId;
    private LocalDateTime unregisteredAt;
}
