package com.ecommerce.order.repository;

import com.ecommerce.order.entity.UserOrderProductEntity;
import com.ecommerce.order.entity.enums.UserOrderProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderProductRepository extends JpaRepository<UserOrderProductEntity, Long> {

    List<UserOrderProductEntity> findAllByUserOrderIdAndStatusOrderByIdDesc(Long userOrderId, UserOrderProductStatus status);
}
