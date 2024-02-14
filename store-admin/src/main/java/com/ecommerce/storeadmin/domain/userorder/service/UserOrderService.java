package com.ecommerce.storeadmin.domain.userorder.service;

import com.ecommerce.db.userorder.UserOrderEntity;
import com.ecommerce.db.userorder.UserOrderRepository;
import com.ecommerce.storeadmin.common.error.ErrorCode;
import com.ecommerce.storeadmin.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    /**
     * 사용자 주문의 ID에 해당하는 사용자 주문 조회
     *
     * @param id 조회할 사용자 주문의 ID
     * @return 조회된 사용자 주문 엔티티
     * @throws ApiException 사용자 주문이 존재하지 않을 경우 발생하는 예외
     */
    public UserOrderEntity getUserOrder(Long id) {
        return userOrderRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "UserOrder not found"));
    }

}
