package com.ecommerce.store.domain.store.service;

import com.ecommerce.common.model.enums.StoreCategory;
import com.ecommerce.common.model.enums.StoreStatus;
import com.ecommerce.store.axon.command.CreateStoreCommand;
import com.ecommerce.store.axon.command.DeleteStoreCommand;
import com.ecommerce.store.axon.command.UpdateStoreCommand;
import com.ecommerce.store.common.error.ErrorCode;
import com.ecommerce.store.common.error.StoreErrorCode;
import com.ecommerce.store.common.exception.ApiException;
import com.ecommerce.store.domain.store.controller.model.StoreCreateRequest;
import com.ecommerce.store.domain.store.controller.model.StoreDeleteRequest;
import com.ecommerce.store.domain.store.controller.model.StoreUpdateRequest;
import com.ecommerce.store.entity.StoreEntity;
import com.ecommerce.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final CommandGateway commandGateway;
    private final StoreRepository storeRepository;
    private final Configuration configuration;

    @Override
    public String createStore(StoreCreateRequest request) {
        String storeId = UUID.randomUUID().toString();
        LocalDateTime registeredAt = LocalDateTime.now();

        CreateStoreCommand command = new CreateStoreCommand(
                storeId,
                request.getName(),
                request.getAddress(),
                request.getCategory(),
                request.getThumbnailUrl(),
                request.getPhoneNumber(),
                request.getIsDoubleChecked(),
                registeredAt
        );

        try {
            commandGateway.send(command).get(); // 명령 완료 대기
            log.info("상점 생성 성공 : " + storeId);
            return "상점 생성 성공 : " + storeId;
        } catch (InterruptedException ex) {
            String errorMessage = "상점 생성 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(StoreErrorCode.STORE_CREATE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "상점 생성 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(StoreErrorCode.STORE_CREATE_FAIL, errorMessage, ex);
        }
    }

    @Override
    public String updateStore(StoreUpdateRequest request) {
        String storeId = request.getStoreId();
        LocalDateTime updatedAt = LocalDateTime.now();

        UpdateStoreCommand command = new UpdateStoreCommand(
                storeId,
                request.getName(),
                request.getAddress(),
                request.getCategory(),
                request.getStar(),
                request.getThumbnailUrl(),
                request.getPhoneNumber(),
                request.getIsDoubleChecked(),
                updatedAt
        );

        try {
            commandGateway.send(command).get(); // 명령 완료 대기
            log.info("상점 수정 성공 : " + storeId);
            return "상점 수정 성공 : " + storeId;
        } catch (InterruptedException ex) {
            String errorMessage = "상점 수정 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(StoreErrorCode.STORE_UPDATE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "상점 수정 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(StoreErrorCode.STORE_UPDATE_FAIL, errorMessage, ex);
        }
    }

    @Override
    public String deleteStore(StoreDeleteRequest request) {
        String storeId = request.getStoreId();
        LocalDateTime unregisteredAt = LocalDateTime.now();

        DeleteStoreCommand command = new DeleteStoreCommand(
                storeId,
                unregisteredAt
        );

        try {
            commandGateway.send(command).get(); // 명령 완료 대기
            log.info("상점 삭제 성공 : " + storeId);
            return "상점 삭제 성공 : " + storeId;
        } catch (InterruptedException ex) {
            String errorMessage = "상점 삭제 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(StoreErrorCode.STORE_DELETE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "상점 삭제 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(StoreErrorCode.STORE_DELETE_FAIL, errorMessage, ex);
        }
    }

    /**
     * 상점 이름을 사용해 상점의 중복 여부 확인
     *
     * @param name 확인할 상점의 이름
     * @return 상점이 존재하는지 여부를 나타내는 Boolean 값
     */
    @Override
    public Boolean doubleCheckStore(String name) {
        Optional<StoreEntity> entity = storeRepository.findFirstByNameAndStatusOrderByStoreIdDesc(
                name,
                StoreStatus.REGISTERED
        );

        return entity.isPresent();
    }

    /**
     * 특정 카테고리에 속하는 모든 상점 반환
     *
     * @param category 조회할 상점 카테고리
     * @return 조회된 상점 엔터티 리스트
     */
    @Override
    public List<StoreEntity> getStoreByCategory(StoreCategory category) {
        return storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED,
                category
        );
    }

    /**
     * 모든 상점의 이벤트 처리 재설정
     */
    @Override
    public void resetStore() {
        configuration.eventProcessingConfiguration()
                .eventProcessorByProcessingGroup("stores", TrackingEventProcessor.class)
                .ifPresent(trackingEventProcessor -> {
                    trackingEventProcessor.shutDown();
                    trackingEventProcessor.resetTokens();
                    trackingEventProcessor.start();
                });
    }

    /**
     * 특정 상점 ID에 해당하는 상점 반환
     *
     * @param storeId 조회할 상점의 ID
     * @return 조회된 상점 엔터티
     * @throws ApiException 상점이 존재하지 않거나 유효하지 않은 경우 발생하는 예외
     */
    @Override
    public StoreEntity getStoreWithThrow(String storeId) {
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByStoreIdAndStatusOrderByStoreIdDesc(
                storeId,
                StoreStatus.REGISTERED
        );

        return storeEntity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    /**
     * 모든 등록된 상점 반환
     *
     * @return 등록된 모든 상점 엔터티 리스트
     */
    @Override
    public List<StoreEntity> searchAll() {
        return storeRepository.findAllByStatusOrderByStoreIdDesc(StoreStatus.REGISTERED);
    }
}
