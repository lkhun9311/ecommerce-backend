package com.ecommerce.product.repository;

import com.ecommerce.common.model.enums.StoreProductStatus;
import com.ecommerce.product.entity.StoreProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StoreProductRepository extends JpaRepository<StoreProductEntity, String> {

    // 유효한 상품 가져오기
    Optional<StoreProductEntity> findFirstByStoreProductIdAndStatusOrderByStoreProductIdDesc(String storeProductId, StoreProductStatus status);

    // 특정 상점의 상품 전부 가져오기
    List<StoreProductEntity> findAllByStoreIdAndStatusOrderByStoreIdDesc(String storeId, StoreProductStatus status);

    @Modifying
    @Query("UPDATE StoreProductEntity s " +
            "SET s.storeId = :storeId, s.name = :name, s.amount = :amount, s.thumbnailUrl = :thumbnailUrl " +
            "WHERE s.storeProductId = :storeProductId"
    )
    void updateStoreProductByEvent(
            @Param("storeProductId") String storeProductId,
            @Param("storeId") String storeId,
            @Param("name") String name,
            @Param("amount") BigDecimal amount,
            @Param("thumbnailUrl") String thumbnailUrl
    );

    @Modifying
    @Query("UPDATE StoreProductEntity s " +
            "SET s.status = :status " +
            "WHERE s.storeProductId = :storeProductId"
    )
    void deleteStoreProductByEvent(
            @Param("storeProductId") String storeProductId,
            @Param("status") StoreProductStatus status
    );
}
