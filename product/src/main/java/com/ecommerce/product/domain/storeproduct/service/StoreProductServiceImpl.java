package com.ecommerce.product.domain.storeproduct.service;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreProductServiceImpl implements StoreProductService {
    private final CommandGateway commandGateway;
    private final StoreProductRepository storeProductRepository;
    private final Configuration configuration;

    @Override
    public String createStoreProduct(StoreProductCreateRequest request) {
        String storeProductId = UUID.randomUUID().toString();
        CreateStoreProductCommand command = new CreateStoreProductCommand(
                storeProductId,
                request.getStoreId(),
                request.getName(),
                request.getAmount(),
                request.getThumbnailUrl(),
                request.getColor()
        );

        try {
            commandGateway.send(command).get(); // 명령 완료 대기
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
        String storeProductId = request.getStoreProductId();

        UpdateStoreProductCommand command = new UpdateStoreProductCommand(
                storeProductId,
                request.getStoreId(),
                request.getName(),
                request.getAmount(),
                request.getThumbnailUrl()
        );

        try {
            commandGateway.send(command).get(); // 명령 완료 대기
            log.info("상품 수정 성공 : " + storeProductId);
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
            commandGateway.send(command).get(); // 명령 완료 대기
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
    public List<StoreProductEntity> getStoreProductByStoreId(Long storeId) {
        return storeProductRepository.findAllByStoreIdAndStatusOrderByStoreIdDesc(storeId, StoreProductStatus.REGISTERED);
    }

    @Override
    public StoreProductEntity getStoreProductWithThrow(String storeProductId) {
        Optional<StoreProductEntity> entity = storeProductRepository.findFirstByStoreProductIdAndStatusOrderByStoreProductIdDesc(storeProductId, StoreProductStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(StoreProductErrorCode.STORE_PRODUCT_NULL_POINT_ERROR));
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
