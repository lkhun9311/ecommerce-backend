package com.ecommerce.storeadmin.domain.user.busniess;

import com.ecommerce.db.store.StoreEntity;
import com.ecommerce.db.store.enums.StoreStatus;
import com.ecommerce.db.storeuser.StoreUserEntity;
import com.ecommerce.storeadmin.common.annotation.Business;
import com.ecommerce.storeadmin.common.api.Api;
import com.ecommerce.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import com.ecommerce.storeadmin.domain.user.controller.model.StoreUserResponse;
import com.ecommerce.storeadmin.domain.user.converter.StoreUserConverter;
import com.ecommerce.storeadmin.domain.user.service.StoreService;
import com.ecommerce.storeadmin.domain.user.service.StoreUserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreService storeService;

    /**
     * 사용자가 입력한 정보를 기반으로 상점 사용자 등록
     *
     * @param request 상점 사용자 등록 요청 정보
     * @return 상점 사용자 등록 결과와 응답 엔티티
     */
    public Api<StoreUserResponse> register(StoreUserRegisterRequest request) {
        // request 상점 이름을 통해 등록된 상점 엔티티 조회
        StoreEntity storeEntity = storeService.getStoreWithThrow(request.getStoreName(), StoreStatus.REGISTERED);

        // 상점 사용자 엔티티로 변환
        StoreUserEntity storeUserEntity = storeUserConverter.toEntity(request, storeEntity);

        // 상점 사용자 register 서비스를 통해 상점 사용자 엔티티 등록
        StoreUserEntity newStoreUserEntity = storeUserService.register(storeUserEntity);

        // 등록된 상점 사용자 엔티티와 연결된 가게 정보를 포함한 응답 엔티티 생성
        StoreUserResponse response = storeUserConverter.toResponse(newStoreUserEntity, storeEntity);

        return Api.ok(response);
    }
}
