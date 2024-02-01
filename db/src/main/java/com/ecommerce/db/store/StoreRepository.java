package com.ecommerce.db.store;

import com.ecommerce.db.store.enums.StoreCategory;
import com.ecommerce.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory storeCategory);
}
