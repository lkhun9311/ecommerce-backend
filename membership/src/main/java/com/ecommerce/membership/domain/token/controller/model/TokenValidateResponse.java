package com.ecommerce.membership.domain.token.controller.model;

import com.ecommerce.common.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenValidateResponse {

    private String userId;
    private String name;
    private String email;
    private UserStatus status;
    private String address;
}
