package com.ecommerce.store.repository;

import com.ecommerce.store.entity.StoreEntity;
import com.ecommerce.common.model.enums.StoreCategory;
import com.ecommerce.common.model.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, String> {
    Optional<StoreEntity> findFirstByNameAndStatusOrderByStoreIdDesc(String storeName, StoreStatus status);
    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory storeCategory);
    Optional<StoreEntity> findFirstByStoreIdAndStatusOrderByStoreIdDesc(String storeId, StoreStatus status);
    List<StoreEntity> findAllByStatusOrderByStoreIdDesc(StoreStatus status);

    @Modifying
    @Query("UPDATE StoreEntity s " +
            "SET s.name = :name, s.address = :address, s.category = :category, s.star = :star, s.thumbnailUrl = :thumbnailUrl, s.phoneNumber = :phoneNumber, s.updatedAt = :updatedAt " +
            "WHERE s.storeId = :storeId"
    )
    void updateStoreByEvent(
            @Param("storeId") String storeId,
            @Param("name") String name,
            @Param("address") String address,
            @Param("category") StoreCategory category,
            @Param("star") Double star,
            @Param("thumbnailUrl") String thumbnailUrl,
            @Param("phoneNumber") String phoneNumber,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    @Modifying
    @Query("UPDATE StoreEntity s " +
            "SET s.status = :status, s.unregisteredAt = :unregisteredAt " +
            "WHERE s.storeId = :storeId"
    )
    void deleteStoreByEvent(
            @Param("storeId") String storeId,
            @Param("status") StoreStatus status,
            @Param("unregisteredAt") LocalDateTime unregisteredAt
    );
}
