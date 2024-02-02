package com.ecommerce.db.storeproduct;

import com.ecommerce.db.storeproduct.enums.StoreProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreProductRepository extends JpaRepository<StoreProductEntity, Long> {

    // 유효한 상품 가져오기
    Optional<StoreProductEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreProductStatus status);

    // 특정 상점의 상품 전부 가져오기
    List<StoreProductEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId, StoreProductStatus status);
}
