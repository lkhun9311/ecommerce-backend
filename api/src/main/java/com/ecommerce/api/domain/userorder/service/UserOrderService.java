package com.ecommerce.api.domain.userorder.service;

import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.db.userorder.UserOrderEntity;
import com.ecommerce.db.userorder.UserOrderRepository;
import com.ecommerce.db.userorder.enums.UserOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    /**
     * 주문 ID와 사용자 ID로 특정 주문 내역 조회 (주문 상태 상관 없음)
     *
     * @param id     주문 ID
     * @param userId 사용자 ID
     * @return 특정 주문 내역 엔티티
     * @throws ApiException 조회된 주문 내역이 없을 경우 발생하는 예외
     */
    public UserOrderEntity getUserOrderWithoutThrow(Long id, Long userId) {
        return userOrderRepository.findAllByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 주문 ID와 사용자 ID로 특정 주문 내역 조회 (주문 상태가 REGISTERED인 경우)
     *
     * @param id     주문 ID
     * @param userId 사용자 ID
     * @return 특정 주문 내역 엔티티
     * @throws ApiException 조회된 주문 내역이 없거나 상태가 REGISTERED가 아닌 경우 발생하는 예외
     */
    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId) {
        return userOrderRepository.findAllByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 사용자의 주문 내역 조회 (주문 상태가 REGISTERED인 경우)
     *
     * @param userId 사용자 ID
     * @return 주문 내역 엔티티 목록
     */
    public List<UserOrderEntity> getUserOrderList(Long userId) {
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }

    /**
     * 사용자의 주문 내역 조회 (주문 상태가 주어진 주문 상태 목록에 포함된 경우)
     *
     * @param userId     사용자 ID
     * @param statusList 주문 상태 목록
     * @return 주문 내역 엔티티 목록
     */
    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList) {
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    /**
     * 현재 진행 중인 주문 내역 조회 (주문 상태가 ORDER, ACCEPT, DELIVERY인 경우)
     *
     * @param userId 사용자 ID
     * @return 현재 진행 중인 주문 내역 엔티티 목록
     */
    public List<UserOrderEntity> currentOrderList(Long userId) {
        return getUserOrderList(
                userId,
                List.of(
                        UserOrderStatus.ORDER,
                        UserOrderStatus.ACCEPT,
                        UserOrderStatus.DELIVERY
                )
        );
    }

    /**
     * 과거 주문 내역 조회 (주문 상태가 RECEIVE인 경우)
     *
     * @param userId 사용자 ID
     * @return 과거 주문 내역 엔티티 목록
     */
    public List<UserOrderEntity> historyOrderList(Long userId) {
        return getUserOrderList(
                userId,
                List.of(
                        UserOrderStatus.RECEIVE
                )
        );
    }

    /**
     * 주문을 생성하고 주문 상태를 ORDER로 설정한 뒤 저장
     *
     * @param entity 주문 엔티티
     * @return 저장된 주문 엔티티
     * @throws ApiException 주문 엔티티가 null인 경우 발생하는 예외
     */
    public UserOrderEntity order(UserOrderEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 주문 상태 변경 로직
     *
     * @param entity 주문 엔티티
     * @param status 변경할 주문 상태
     * @return 저장된 주문 엔티티
     */
    public UserOrderEntity setStatus(UserOrderEntity entity, UserOrderStatus status) {
        entity.setStatus(status);
        return userOrderRepository.save(entity);
    }

    /**
     * 주문 상태 변경 (주문 확인)
     * 주문 상태를 ACCEPT로 변경한 뒤 저장
     *
     * @param entity 주문 엔티티
     * @return 저장된 주문 엔티티
     */
    public UserOrderEntity accept(UserOrderEntity entity) {
        entity.setAcceptedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.ACCEPT);
    }

    /**
     * 주문 상태 변경 (배송 중)
     * 주문 상태를 DELIVERY로 변경한 뒤 저장
     *
     * @param entity 주문 엔티티
     * @return 저장된 주문 엔티티
     */
    public UserOrderEntity delivery(UserOrderEntity entity) {
        entity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.DELIVERY);
    }

    /**
     * 주문 상태 변경 (배송 완료)
     * 주문 상태를 RECEIVE로 변경한 뒤 저장
     *
     * @param entity 주문 엔티티
     * @return 저장된 주문 엔티티
     */
    public UserOrderEntity receive(UserOrderEntity entity) {
        entity.setReceivedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.RECEIVE);
    }
}
