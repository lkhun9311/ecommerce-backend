package com.ecommerce.store.domain.store.service;

import com.ecommerce.common.model.enums.StoreCategory;
import com.ecommerce.store.domain.store.controller.model.StoreCreateRequest;
import com.ecommerce.store.domain.store.controller.model.StoreDeleteRequest;
import com.ecommerce.store.domain.store.controller.model.StoreUpdateRequest;
import com.ecommerce.store.entity.StoreEntity;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    /**
     * 상점 생성
     *
     * @param request 상점 생성 요청 객체
     * @return 생성된 상점의 식별자
     */
    String createStore(StoreCreateRequest request);

    /**
     * 상점 수정
     *
     * @param request 상점 수정 요청 객체
     * @return 수정된 상점의 식별자
     */
    String updateStore(StoreUpdateRequest request);

    /**
     * 상점 삭제
     *
     * @param request 상점 삭제 요청 객체
     * @return 삭제된 상점의 식별자
     */
    String deleteStore(StoreDeleteRequest request);

    /**
     * 상점 이름의 중복 확인
     *
     * @param name 중복 확인할 상점의 이름
     * @return 중복 여부를 나타내는 불리언 값
     */
    Boolean doubleCheckStore(String name);

    /**
     * 특정 카테고리의 상점 목록 조회
     *
     * @param category 조회할 상점 카테고리
     * @return 조회된 상점 목록
     */
    List<StoreEntity> getStoreByCategory(StoreCategory category);

    /**
     * 상점 모델 재설정
     */
    void resetStore();

    /**
     * 상점 ID를 기준으로 상점 조회
     *
     * @param storeId 조회할 상점의 식별자
     * @return 조회된 상점 엔터티
     */
    StoreEntity getStoreWithThrow(String storeId);

    /**
     * 상점 ID를 기준으로 상점 조회
     *
     * @param storeId 조회할 상점의 식별자
     * @return 조회된 상점 엔터티
     */
    Optional<StoreEntity> getStoreWithoutThrow(String storeId);

    /**
     * 모든 상점 조회
     *
     * @return 조회된 모든 상점 목록
     */
    List<StoreEntity> searchAll();
}
