package com.ecommerce.product.domain.storeproduct.service;

import com.ecommerce.db.storeproduct.StoreProductEntity;
import com.ecommerce.db.storeproduct.StoreProductRepository;
import com.ecommerce.db.storeproduct.enums.StoreProductStatus;
import com.ecommerce.product.common.error.ErrorCode;
import com.ecommerce.product.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreProductService {

    private final StoreProductRepository storeProductRepository;

    /**
     * 지정된 ID와 상품 상태가 REGISTERED에 해당하는 상품 엔티티를 가져오는 메서드
     *
     * @param id 상품 ID
     * @return 상품 엔티티
     * @throws ApiException NULL_POINT_ERROR: 상품 엔티티가 존재하지 않을 경우
     */
    public StoreProductEntity getStoreProductWithThrow(Long id) {
        Optional<StoreProductEntity> entity = storeProductRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreProductStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 지정된 상점 ID와 상품 상태가 REGISTERED에 해당하는 상품 엔티티 목록을 가져오는 메서드
     *
     * @param storeId 상점 ID
     * @return 상품 엔티티 목록
     */
    public List<StoreProductEntity> getStoreProductByStoreId(Long storeId) {
        return storeProductRepository.findAllByStoreIdAndStatusOrderByStoreIdDesc(storeId, StoreProductStatus.REGISTERED);
    }

    /**
     * 상품 엔티티를 등록하는 메서드
     *
     * @param storeProductEntity 등록할 상품 엔티티
     * @return 등록된 상품 엔티티
     * @throws ApiException NULL_POINT_ERROR: 상품 엔티티가 null일 경우
     */
    public StoreProductEntity register(StoreProductEntity storeProductEntity) {
        return Optional.ofNullable(storeProductEntity)
                .map(it -> {
                    it.setStatus(StoreProductStatus.REGISTERED);
                    return storeProductRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }
}
