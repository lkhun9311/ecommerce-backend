package com.ecommerce.membership.domain.token.controller.model;

import com.ecommerce.membership.domain.token.model.TokenValidateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidateRequest {

    @NotNull
    private TokenValidateDto tokenValidateDto;
}
