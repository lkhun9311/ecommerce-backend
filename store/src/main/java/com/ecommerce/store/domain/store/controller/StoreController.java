package com.ecommerce.store.domain.store.controller;

import com.ecommerce.store.common.annotation.UserSession;
import com.ecommerce.store.common.api.Api;
import com.ecommerce.store.domain.store.controller.model.StoreCreateRequest;
import com.ecommerce.store.domain.store.business.StoreBusiness;
import com.ecommerce.store.domain.store.controller.model.StoreDeleteRequest;
import com.ecommerce.store.domain.store.controller.model.StoreUpdateRequest;
import com.ecommerce.store.resolver.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreBusiness storeBusiness;

    /**
     * 상점 생성
     *
     * @param request 상점 생성 요청(Api<StoreCreateRequest>)
     * @return 상점 생성 결과를 담은 API 객체
     */
    @PostMapping("/create")
    public Api<String> createStore(
            @Valid
            @RequestBody
            Api<StoreCreateRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreCreateRequest body = request.getBody();
        String response = storeBusiness.createStore(body, user);
        return Api.ok(response);
    }

    /**
     * 상점 수정
     *
     * @param request 상점 수정 요청을 담은 API 객체
     * @param user 사용자 세션 정보
     * @return 상점 수정 결과를 담은 API 객체
     */
    @PutMapping("/update")
    public Api<String> updateStore(
            @Valid
            @RequestBody
            Api<StoreUpdateRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreUpdateRequest body = request.getBody();
        String response = storeBusiness.updateStore(body, user);
        return Api.ok(response);
    }

    /**
     * 상점 삭제
     *
     * @param request 상점 삭제 요청을 담은 API 객체
     * @param user 사용자 세션 정보
     * @return 상점 삭제 결과를 담은 API 객체
     */
    @DeleteMapping("/delete")
    public Api<String> deleteStore(
            @Valid
            @RequestBody
            Api<StoreDeleteRequest> request,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        StoreDeleteRequest body = request.getBody();
        String response = storeBusiness.deleteStore(body, user);
        return Api.ok(response);
    }

    /**
     * 상점 이름 중복 확인
     *
     * @param name 중복 확인할 상점의 이름
     * @return 상점의 이름 중복 여부를 담은 API 객체
     */
    @GetMapping("/double-check")
    public Api<Boolean> doubleCheckStore(
            @RequestParam(name = "name")
            String name,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        Boolean response = storeBusiness.doubleCheckStore(name, user);
        return Api.ok(response);
    }

    /**
     * 상점 모델 재설정
     *
     * @param user 사용자 세션 정보
     */
    @PostMapping("/reset")
    public void reset(
            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        storeBusiness.resetStoreProduct();
    }

}
