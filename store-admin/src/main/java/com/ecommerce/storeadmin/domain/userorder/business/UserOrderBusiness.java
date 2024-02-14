package com.ecommerce.storeadmin.domain.userorder.business;

import com.ecommerce.db.userorder.UserOrderEntity;
import com.ecommerce.db.userorderproduct.UserOrderProductEntity;
import com.ecommerce.storeadmin.common.annotation.Business;
import com.ecommerce.storeadmin.common.error.ErrorCode;
import com.ecommerce.storeadmin.common.exception.ApiException;
import com.ecommerce.storeadmin.common.message.UserOrderMessage;
import com.ecommerce.storeadmin.domain.sse.connection.SseConnectionPool;
import com.ecommerce.storeadmin.domain.sse.connection.model.UserSseConnection;
import com.ecommerce.storeadmin.domain.storeproduct.controller.model.StoreProductResponse;
import com.ecommerce.storeadmin.domain.storeproduct.converter.StoreProductConverter;
import com.ecommerce.storeadmin.domain.storeproduct.service.StoreProductService;
import com.ecommerce.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import com.ecommerce.storeadmin.domain.userorder.controller.model.UserOrderResponse;
import com.ecommerce.storeadmin.domain.userorder.converter.UserOrderConverter;
import com.ecommerce.storeadmin.domain.userorder.service.UserOrderService;
import com.ecommerce.storeadmin.domain.userorderproduct.service.UserOrderProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;
    private final UserOrderProductService userOrderProductService;
    private final StoreProductService storeProductService;
    private final StoreProductConverter storeProductConverter;

    /**
     * 사용자 주문을 처리하고 SSE 연결을 통해 주문 정보(event) 전송
     *
     * @param message 사용자 주문 메시지(사용자 주문 ID 포함)
     */
    public void pushUserOrder(UserOrderMessage message) {
        // 사용자 주문 엔티티 조회
        UserOrderEntity userOrderEntity = userOrderService.getUserOrder(message.getUserOrderId());

        // 사용자 주문에 포함된 상품 목록 조회
        List<UserOrderProductEntity> userOrderProductList = userOrderProductService.getUserOrderProductList(userOrderEntity.getId());

        // 사용자 주문 정보를 응답 객체로 변환
        UserOrderResponse userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // 사용자 주문에 포함된 상품의 정보를 응답 객체로 변환
        List<StoreProductResponse> storeProductResponseList = userOrderProductList.stream()
                .map(UserOrderProductEntity::getStoreProductId)
                .map(storeProductService::getStoreProductWithThrow)
                .map(storeProductConverter::toResponse)
                .collect(Collectors.toList());

        // 사용자 주문 상세 정보(data) 생성
        UserOrderDetailResponse data = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeProductResponseList(storeProductResponseList)
                .build();

        // 주문을 처리한 상점의 SSE 연결 조회
        UserSseConnection userSseConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // SSE 연결이 존재할 경우 사용자 주문 상세 정보(data) 전송
        Optional.ofNullable(userSseConnection)
                .ifPresentOrElse(
                        it -> it.sendEvent(data),
                        () -> {
                            throw new ApiException(ErrorCode.NULL_POINT_ERROR, "user sse connection not found");
                        }
                );

        log.info("push user order >> {}", data);
    }
}
