package com.ecommerce.storeadmin.domain.userorder.controller.model;

import com.ecommerce.db.userorder.enums.UserOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderResponse {

    private Long id;

    private Long userId;

    private Long storeId;

    private UserOrderStatus status;

    private BigDecimal amount;

    private LocalDateTime orderedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime deliveryStartedAt;

    private LocalDateTime receivedAt;
}
