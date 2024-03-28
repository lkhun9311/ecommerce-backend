package com.ecommerce.product.axon.projection;

import com.ecommerce.common.axon.event.storeproduct.StoreProductCreatedEvent;
import com.ecommerce.common.axon.event.storeproduct.StoreProductDeletedEvent;
import com.ecommerce.common.axon.event.storeproduct.StoreProductUpdatedEvent;
import com.ecommerce.common.model.enums.StoreProductStatus;
import com.ecommerce.product.domain.storeproduct.converter.StoreProductConverter;
import com.ecommerce.product.entity.StoreProductEntity;
import com.ecommerce.product.repository.StoreProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@ProcessingGroup("store_products")
@Slf4j
@Component
@RequiredArgsConstructor
public class StoreProductProjection {
    private final StoreProductRepository storeProductRepository;
    private final StoreProductConverter storeProductConverter;

    @ResetHandler
    private void resetStoreProductModel() {
        log.info("reset StoreProduct Model triggered");
        storeProductRepository.deleteAll();
    }

    @AllowReplay
    @EventHandler
    public void on(StoreProductCreatedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 상품 생성 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        StoreProductEntity entity = storeProductConverter.toEntity(event);
        entity.setStatus(StoreProductStatus.REGISTERED);
        storeProductRepository.save(entity);
    }

    @AllowReplay
    @EventHandler
    public void on(StoreProductUpdatedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 상품 수정 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        storeProductRepository.updateStoreProductByEvent(
                event.getStoreProductId(),
                event.getStoreId(),
                event.getName(),
                event.getAmount(),
                event.getThumbnailUrl()
        );
    }

    @AllowReplay
    @EventHandler
    public void on(StoreProductDeletedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 상품 삭제 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        storeProductRepository.deleteStoreProductByEvent(
                event.getStoreProductId(),
                StoreProductStatus.UNREGISTERED
        );
    }
}
