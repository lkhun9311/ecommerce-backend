package com.ecommerce.api.domain.userorderproduct.service;

import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.db.userorderproduct.UserOrderProductEntity;
import com.ecommerce.db.userorderproduct.UserOrderProductRepository;
import com.ecommerce.db.userorderproduct.enums.UserOrderProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderProductService {

    private final UserOrderProductRepository userOrderProductRepository;

    /**
     * 특정 사용자 주문에 속한 주문 상품 목록 조회
     *
     * @param userOrderId 사용자 주문 ID
     * @return 주문 상품 엔티티 목록
     */
    public List<UserOrderProductEntity> getUserOrderProduct(Long userOrderId) {
        return userOrderProductRepository.findAllByUserOrderIdAndStatusOrderByIdDesc(userOrderId, UserOrderProductStatus.REGISTERED);
    }

    /**
     * 주문 상품을 주문 처리
     * 주문 상태를 REGISTERED로 설정한 뒤 저장
     *
     * @param entity 주문 상품 엔티티
     * @return 저장된 주문 상품 엔티티
     * @throws ApiException 주문 상품 엔티티가 null인 경우 발생하는 예외
     */
    public UserOrderProductEntity order(UserOrderProductEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    it.setStatus(UserOrderProductStatus.REGISTERED);
                    return userOrderProductRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }
}
