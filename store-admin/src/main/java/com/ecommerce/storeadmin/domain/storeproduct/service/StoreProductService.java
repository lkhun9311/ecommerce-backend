package com.ecommerce.storeadmin.domain.storeproduct.service;

import com.ecommerce.db.storeproduct.StoreProductEntity;
import com.ecommerce.db.storeproduct.StoreProductRepository;
import com.ecommerce.db.storeproduct.enums.StoreProductStatus;
import com.ecommerce.storeadmin.common.error.ErrorCode;
import com.ecommerce.storeadmin.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreProductService {

    private final StoreProductRepository storeProductRepository;

    /**
     * 상품의 ID에 해당하는 상품 조회
     *
     * @param id 조회할 상품의 ID
     * @return 조회된 상품 엔티티
     * @throws ApiException 상품이 존재하지 않을 경우 발생하는 예외
     */
    public StoreProductEntity getStoreProductWithThrow(Long id){
        return storeProductRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreProductStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "StoreProduct not found"));
    }
}
