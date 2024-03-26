package com.ecommerce.membership.domain.user.controller.model;

import com.ecommerce.common.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private UserStatus status;
    private String address;
    private String thumbnailUrl;
    private String phoneNumber;
    private Boolean isDoubleChecked;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime updatedAt;
}
