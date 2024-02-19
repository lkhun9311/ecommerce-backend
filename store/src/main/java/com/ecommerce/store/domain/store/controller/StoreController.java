package com.ecommerce.store.domain.store.controller;

import com.ecommerce.store.common.annotation.UserSession;
import com.ecommerce.store.common.api.Api;
import com.ecommerce.store.domain.store.controller.model.StoreResponse;
import com.ecommerce.store.domain.store.business.StoreBusiness;
import com.ecommerce.store.domain.store.controller.model.StoreRegisterRequest;
import com.ecommerce.store.resolver.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreBusiness storeBusiness;

    /**
     * 상점 등록 엔드포인트
     *
     * @param request 상점 등록 요청(Api<StoreRegisterRequest>)
     * @return 상점 등록 결과 응답(Api<StoreResponse>)
     */
    @PostMapping("/register")
    public Api<StoreResponse> register(
            @Valid
            @RequestBody
            Api<StoreRegisterRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreResponse response = storeBusiness.register(request.getBody(), user);
        return Api.ok(response);
    }
}
