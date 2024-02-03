package com.ecommerce.api.domain.userorder.business;

import com.ecommerce.api.common.annotation.Business;
import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.store.converter.StoreConverter;
import com.ecommerce.api.domain.store.service.StoreService;
import com.ecommerce.api.domain.storeproduct.converter.StoreProductConverter;
import com.ecommerce.api.domain.storeproduct.service.StoreProductService;
import com.ecommerce.api.domain.user.model.User;
import com.ecommerce.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.ecommerce.api.domain.userorder.controller.model.UserOrderRequest;
import com.ecommerce.api.domain.userorder.controller.model.UserOrderResponse;
import com.ecommerce.api.domain.userorder.converter.UserOrderConverter;
import com.ecommerce.api.domain.userorder.service.UserOrderService;
import com.ecommerce.api.domain.userorderproduct.converter.UserOrderProductConverter;
import com.ecommerce.api.domain.userorderproduct.service.UserOrderProductService;
import com.ecommerce.db.store.StoreEntity;
import com.ecommerce.db.storeproduct.StoreProductEntity;
import com.ecommerce.db.userorder.UserOrderEntity;
import com.ecommerce.db.userorderproduct.UserOrderProductEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderProductService userOrderProductService;
    private final StoreProductService storeProductService;
    private final StoreService storeService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderProductConverter userOrderProductConverter;
    private final StoreProductConverter storeProductConverter;
    private final StoreConverter storeConverter;

    /**
     * 사용자의 주문을 처리하고 응답 반환
     *
     * @param user 사용자 정보
     * @param body 주문 요청 정보
     * @return 주문 응답 정보
     */
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        List<StoreProductEntity> storeProductEntityList = body.getStoreProductIdList().stream()
                .map(storeProductService::getStoreProductWithThrow)
                .collect(Collectors.toList());
        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeProductEntityList);
        UserOrderEntity newUserOrderEntity = userOrderService.order(userOrderEntity);

        List<UserOrderProductEntity> userOrderProductEntityList = storeProductEntityList.stream()
                .map(it -> userOrderProductConverter.toEntity(newUserOrderEntity, it))
                .collect(Collectors.toList());

        userOrderProductEntityList.forEach(userOrderProductService::order);

        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    /**
     * 특정 주문에 대한 정보 처리 로직
     *
     * @param userOrderEntity 사용자 주문 엔티티
     * @return 주문 상세 응답 정보
     */
    private UserOrderDetailResponse processOrder(UserOrderEntity userOrderEntity) {
        // 특정 주문의 상품 리스트 가져오기
        List<UserOrderProductEntity> userOrderProductEntityList = userOrderProductService.getUserOrderProduct(userOrderEntity.getId());

        // 주문한 상점의 상품 리스트 가져오기
        List<StoreProductEntity> storeProductEntityList = userOrderProductEntityList.stream()
                .map(userOrderProductEntity ->
                        storeProductService.getStoreProductWithThrow(userOrderProductEntity.getStoreProductId())
                )
                .collect(Collectors.toList());

        // 사용자가 최근 주문한 상점의 상품 가져오기
        Optional<StoreProductEntity> firstEntity = storeProductEntityList.stream().findFirst();

        // 사용자가 최근 주문한 상점 가져오기
        StoreEntity storeEntity;
        if (firstEntity.isPresent()) {
            storeEntity = storeService.getStoreWithThrow(firstEntity.get().getStoreId());
        } else {
            throw new ApiException(ErrorCode.NULL_POINT_ERROR);
        }

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeProductResponseList(storeProductConverter.toRespnonse(storeProductEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }

    /**
     * 사용자의 현재 주문 내역 조회
     *
     * @param user 사용자 정보
     * @return 현재 주문 내역에 대한 상세 응답 리스트
     */
    public List<UserOrderDetailResponse> currentOrder(User user) {
        List<UserOrderEntity> userOrderEntityList = userOrderService.currentOrderList(user.getId());
        return userOrderEntityList.stream()
                .map(this::processOrder)
                .collect(Collectors.toList());
    }

    /**
     * 사용자의 과거 주문 내역 조회
     *
     * @param user 사용자 정보
     * @return 과거 주문 내역에 대한 상세 응답 리스트
     */
    public List<UserOrderDetailResponse> orderhistory(User user) {
        List<UserOrderEntity> userOrderEntityList = userOrderService.historyOrderList(user.getId());
        return userOrderEntityList.stream()
                .map(this::processOrder)
                .collect(Collectors.toList());
    }

    /**
     * 특정 주문에 대한 상세 정보 조회
     * @param user 사용자 정보
     * @param orderId 주문 ID
     * @return 특정 주문에 대한 상세 응답
     */
    public UserOrderDetailResponse readOrder(User user, Long orderId) {
        UserOrderEntity userOrderEntity = userOrderService.getUserOrderWithoutThrow(orderId, user.getId());
        return processOrder(userOrderEntity);
    }
}
