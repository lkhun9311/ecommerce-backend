package com.ecommerce.api.domain.userorder.controller;

import com.ecommerce.api.common.annotation.UserSession;
import com.ecommerce.api.common.api.Api;
import com.ecommerce.api.domain.user.model.User;
import com.ecommerce.api.domain.userorder.business.UserOrderBusiness;
import com.ecommerce.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.ecommerce.api.domain.userorder.controller.model.UserOrderRequest;
import com.ecommerce.api.domain.userorder.controller.model.UserOrderResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-order")
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    /**
     * 상품 주문 처리
     *
     * @param request 주문 요청 정보
     * @param user    현재 로그인한 사용자 정보
     * @return 주문 처리 결과를 담은 응답
     */
    @PostMapping("/order")
    public Api<UserOrderResponse> userOrder(
            @Valid
            @RequestBody
            Api<UserOrderRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        UserOrderResponse response = userOrderBusiness.userOrder(user, request.getBody());
        return Api.ok(response);
    }

    /**
     * 현재 진행 중인 주문 내역 조회
     *
     * @param user 현재 로그인한 사용자 정보
     * @return 현재 주문 내역 조회 결과를 담은 응답
     */
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> currentOrder(
            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        List<UserOrderDetailResponse> responseList = userOrderBusiness.currentOrder(user);
        return Api.ok(responseList);
    }

    /**
     * 과거 주문 내역 조회
     *
     * @param user 현재 로그인한 사용자 정보
     * @return 과거 주문 내역 조회 결과를 담은 응답
     */
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> orderHistory(
            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        List<UserOrderDetailResponse> responseList = userOrderBusiness.orderHistory(user);
        return Api.ok(responseList);
    }

    /**
     * 특정 주문 내역 조회
     *
     * @param user    현재 로그인한 사용자 정보
     * @param orderId 조회할 주문의 ID
     * @return 특정 주문 내역 조회 결과를 담은 응답
     */
    @GetMapping("/id/{orderId}")
    public Api<UserOrderDetailResponse> readOrder(
            @Parameter(hidden = true)
            @UserSession
            User user,

            @PathVariable
            Long orderId
    ) {
        UserOrderDetailResponse responseList = userOrderBusiness.readOrder(user, orderId);
        return Api.ok(responseList);
    }
}
