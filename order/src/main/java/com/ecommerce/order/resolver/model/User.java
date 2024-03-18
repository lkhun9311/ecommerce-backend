package com.ecommerce.order.resolver.model;

import com.ecommerce.order.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String name;

    private String email;

    private UserStatus status;

    private String address;
}
