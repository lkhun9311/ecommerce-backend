package com.ecommerce.db.userorderproduct;

import com.ecommerce.db.userorderproduct.enums.UserOrderProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderProductRepository extends JpaRepository<UserOrderProductEntity, Long> {

    List<UserOrderProductEntity> findAllByUserOrderIdAndStatusOrderByIdDesc(Long userOrderId, UserOrderProductStatus status);
}
