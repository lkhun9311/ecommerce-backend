package com.ecommerce.product.domain.storeproduct.service;

import com.ecommerce.common.axon.query.store.StoreIdQuery;
import com.ecommerce.common.model.enums.StoreProductStatus;
import com.ecommerce.product.axon.command.CreateStoreProductCommand;
import com.ecommerce.product.axon.command.DeleteStoreProductCommand;
import com.ecommerce.product.axon.command.UpdateStoreProductCommand;
import com.ecommerce.product.common.error.StoreProductErrorCode;
import com.ecommerce.product.common.exception.ApiException;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductCreateRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductDeleteRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductUpdateRequest;
import com.ecommerce.product.entity.StoreProductEntity;
import com.ecommerce.product.repository.StoreProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreProductServiceImpl implements StoreProductService {
    private final CommandGateway commandGateway;
    private final StoreProductRepository storeProductRepository;
    private final Configuration configuration;
    private final QueryGateway queryGateway;

    @Override
    public String createStoreProduct(StoreProductCreateRequest request) {
        String storeId = request.getStoreId();
        Boolean isCheckedStoreId = getStoreIdChecked(storeId).join();
        log.info("상점 ID 확인 결과 : " + isCheckedStoreId);
        if (Objects.equals(isCheckedStoreId, Boolean.FALSE)) {
            throw new ApiException(StoreProductErrorCode.STORE_NOT_FOUND);
        }

        String storeProductId = UUID.randomUUID().toString();

        CreateStoreProductCommand command = new CreateStoreProductCommand(
                storeProductId,
                storeId,
                request.getName(),
                request.getAmount(),
                request.getThumbnailUrl(),
                request.getColor()
        );

        try {
            commandGateway.send(command).get();
            log.info("상품 생성 성공 : " + storeProductId);
            return "상품 생성 성공 : " + storeProductId;
        } catch (InterruptedException ex) {
            String errorMessage = "상품 생성 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_CREATE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "상품 생성 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_CREATE_FAIL, errorMessage, ex);
        }
    }

    @Override
    public String updateStoreProduct(StoreProductUpdateRequest request) {
        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        String storeProductId = request.getStoreProductId();

        UpdateStoreProductCommand command = new UpdateStoreProductCommand(
                storeProductId,
                request.getStoreId(),
                request.getName(),
                request.getAmount(),
                request.getThumbnailUrl()
        );

        try {
            commandGateway.send(command).get();

            long endTime = System.currentTimeMillis(); // 종료 시간 기록
            long elapsedTime = endTime - startTime; // 경과 시간 계산

            log.info("상품 수정 성공 : " + storeProductId);

            log.info("상품 수정에 걸리는 시간 : {}", elapsedTime);

            return "상품 수정 성공 : " + storeProductId;
        } catch (InterruptedException ex) {
            String errorMessage = "상품 수정 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_DELETE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "상품 수정 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_DELETE_FAIL, errorMessage, ex);
        }
    }

    @Override
    public String deleteStoreProduct(StoreProductDeleteRequest request) {
        String storeProductId = request.getStoreProductId();

        DeleteStoreProductCommand command = new DeleteStoreProductCommand(storeProductId);

        try {
            commandGateway.send(command).get();
            log.info("상품 삭제 성공 : " + storeProductId);
            return "상품 삭제 성공 : " + storeProductId;
        } catch (InterruptedException ex) {
            String errorMessage = "상품 삭제 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_DELETE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "상품 삭제 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_DELETE_FAIL, errorMessage, ex);
        }
    }

    @Override
    public List<StoreProductEntity> getStoreProductByStoreId(String storeId) {
        return storeProductRepository.findAllByStoreIdAndStatusOrderByStoreIdDesc(storeId, StoreProductStatus.REGISTERED);
    }

    @Override
    public StoreProductEntity getStoreProductWithThrow(String storeProductId) {
        Optional<StoreProductEntity> entity = storeProductRepository.findFirstByStoreProductIdAndStatusOrderByStoreProductIdDesc(storeProductId, StoreProductStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(StoreProductErrorCode.STORE_PRODUCT_NULL_POINT_ERROR));
    }

    @Override
    public CompletableFuture<Boolean> getStoreIdChecked(String storeId) {
        StoreIdQuery storeIdQuery = new StoreIdQuery(storeId);
        log.info("handling {}", storeIdQuery);

        return queryGateway.query(storeIdQuery, ResponseTypes.instanceOf(Boolean.class))
                .exceptionally(ex -> {
                    log.error("상점 정보를 가져오는 도중 오류가 발생했습니다: {}", ex.getMessage());
                    throw new IllegalArgumentException("상점 정보를 가져오는 데 실패했습니다", ex);
                });
    }

    @Override
    public void resetStoreProduct() {
        configuration.eventProcessingConfiguration()
                .eventProcessorByProcessingGroup("store_products", TrackingEventProcessor.class)
                .ifPresent(trackingEventProcessor -> {
                    trackingEventProcessor.shutDown();
                    trackingEventProcessor.resetTokens();
                    trackingEventProcessor.start();
                });
    }
}
