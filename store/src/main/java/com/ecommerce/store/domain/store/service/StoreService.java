package com.ecommerce.store.domain.store.service;

import com.ecommerce.store.common.error.ErrorCode;
import com.ecommerce.store.common.exception.ApiException;
import com.ecommerce.store.entity.StoreEntity;
import com.ecommerce.store.entity.enums.StoreCategory;
import com.ecommerce.store.entity.enums.StoreStatus;
import com.ecommerce.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 지정된 스토어 ID로 유효한 스토어 엔터티를 가져오는 메소드
     *
     * @param storeId 가져올 스토어의 ID
     * @return 스토어 엔터티
     * @throws ApiException 스토어가 존재하지 않거나 유효하지 않은 경우 발생하는 예외
     */
    public StoreEntity getStoreWithThrow(Long storeId) {
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
                storeId,
                StoreStatus.REGISTERED
        );
        return storeEntity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 주어진 스토어 엔터티를 등록하는 메소드
     *
     * @param storeEntity 등록할 스토어 엔터티
     * @return 등록된 스토어 엔터티
     * @throws ApiException 스토어 엔터티가 null인 경우 발생하는 예외
     */
    public StoreEntity register(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity)
                .map(it -> {
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    it.setRegisteredAt(LocalDateTime.now());
                    return storeRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 지정된 카테고리로 스토어를 검색하는 메소드
     *
     * @param storeCategory 검색할 스토어 카테고리
     * @return 검색된 스토어 엔터티 리스트
     */
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory) {
        return storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED,
                storeCategory
        );
    }

    /**
     * 모든 유효한 스토어를 검색하는 메소드
     *
     * @return 검색된 스토어 엔터티 리스트
     */
    public List<StoreEntity> searchAll() {
        return storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
    }
}
