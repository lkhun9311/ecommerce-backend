package com.ecommerce.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {

    @NotNull
    private List<Long> storeProductIdList;
}
