package com.ecommerce.storeadmin.domain.storeuser.service;

import com.ecommerce.db.store.StoreEntity;
import com.ecommerce.db.store.StoreRepository;
import com.ecommerce.db.store.enums.StoreStatus;
import com.ecommerce.storeadmin.common.error.ErrorCode;
import com.ecommerce.storeadmin.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 상점 이름과 상점 상태에 해당하는 상점 엔티티 조회
     *
     * @param storeName   조회할 상점 이름
     * @param storeStatus 조회할 상점 상태
     * @return 조회된 상점 엔티티
     * @throws ApiException ErrorCode.NULL_POINT_ERROR 코드와 함께 발생하는 예외
     */
    public StoreEntity getStoreWithThrow(String storeName, StoreStatus storeStatus) {
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(
                storeName,
                storeStatus
        );
        return storeEntity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }
}
