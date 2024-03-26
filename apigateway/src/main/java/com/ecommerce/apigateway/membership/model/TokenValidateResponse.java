package com.ecommerce.apigateway.membership.model;

import com.ecommerce.apigateway.membership.model.enums.UserStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
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
