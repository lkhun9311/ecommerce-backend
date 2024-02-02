package com.ecommerce.db.userorderproduct;

import com.ecommerce.db.BaseEntity;
import com.ecommerce.db.userorderproduct.enums.UserOrderProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "user_order_product")
public class UserOrderProductEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userOrderId;

    @Column(nullable = false)
    private Long storeProductId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private UserOrderProductStatus status;
}
