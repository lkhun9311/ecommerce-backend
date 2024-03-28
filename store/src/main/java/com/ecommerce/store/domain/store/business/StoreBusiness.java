package com.ecommerce.store.domain.store.business;

import com.ecommerce.common.model.user.User;
import com.ecommerce.store.common.annotation.Business;
import com.ecommerce.store.common.error.UserErrorCode;
import com.ecommerce.store.common.exception.ApiException;
import com.ecommerce.store.domain.store.controller.model.StoreCreateRequest;
import com.ecommerce.store.domain.store.controller.model.StoreDeleteRequest;
import com.ecommerce.store.domain.store.controller.model.StoreResponse;
import com.ecommerce.store.domain.store.controller.model.StoreUpdateRequest;
import com.ecommerce.store.domain.store.service.StoreService;
import com.ecommerce.store.domain.store.converter.StoreConverter;
import com.ecommerce.store.entity.StoreEntity;
import com.ecommerce.common.model.enums.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    /**
     * 상점 생성
     *
     * @param request 상점 생성 요청 객체
     * @param user 현재 사용자 객체
     * @return 생성된 상점의 식별자
     * @throws ApiException 사용자 세션을 찾을 수 없는 경우 발생하는 예외
     */
    public String createStore(StoreCreateRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }
        return storeService.createStore(request);
    }

    /**
     * 상점 정보 수정
     *
     * @param request 상점 수정 요청 객체
     * @param user 현재 사용자 객체
     * @return 수정된 상점의 식별자
     * @throws ApiException 사용자 세션을 찾을 수 없는 경우 발생하는 예외
     */
    public String updateStore(StoreUpdateRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }
        return storeService.updateStore(request);
    }

    /**
     * 상점 삭제
     *
     * @param request 상점 삭제 요청 객체
     * @param user 현재 사용자 객체
     * @return 삭제된 상점의 식별자
     * @throws ApiException 사용자 세션을 찾을 수 없는 경우 발생하는 예외
     */
    public String deleteStore(StoreDeleteRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }
        return storeService.deleteStore(request);
    }

    /**
     * 상점 이름 중복 확인
     *
     * @param name 상점 이름
     * @param user 현재 사용자 객체
     * @return 중복 여부를 나타내는 불리언 값
     * @throws ApiException 사용자 세션을 찾을 수 없는 경우 발생하는 예외
     */
    public Boolean doubleCheckStore(String name, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }
        return storeService.doubleCheckStore(name);
    }

    /**
     * 카테고리별 상점 조회
     *
     * @param category 조회할 상점 카테고리(StoreCategory)
     * @return 조회된 상점 목록 응답 객체 리스트(List<StoreResponse>)
     */
    @Cacheable(cacheNames = "StoreCategory", key = "#category.toString()")
    public List<StoreResponse> searchByCategory(StoreCategory category) {
        List<StoreEntity> storeList = storeService.getStoreByCategory(category);
        return storeList.stream()
                .map(storeConverter::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 상점 정보 재설정
     */
    public void resetStoreProduct() {
        storeService.resetStore();
    }

    /**
     * 상점 ID를 기준으로 상점 조회
     *
     * @param storeId 조회할 상점의 식별자
     * @return 조회된 상점 응답 객체
     */
    @Cacheable(cacheNames = "StoreId", key = "#storeId")
    public StoreResponse searchByStoreId(String storeId) {
        StoreEntity entity = storeService.getStoreWithThrow(storeId);
        return storeConverter.toResponse(entity);
    }

    /**
     * 모든 상점 조회
     *
     * @return 조회된 모든 상점 목록 응답 객체 리스트(List<StoreResponse>)
     */
    @Cacheable(cacheNames = "Store")
    public List<StoreResponse> searchAll() {
        List<StoreEntity> storeList = storeService.searchAll();
        return storeList.stream()
                .map(storeConverter::toResponse)
                .collect(Collectors.toList());
    }
}
