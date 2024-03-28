package com.ecommerce.store.axon.projection;

import com.ecommerce.common.axon.event.store.StoreCreatedEvent;
import com.ecommerce.common.axon.event.store.StoreDeletedEvent;
import com.ecommerce.common.axon.event.store.StoreUpdatedEvent;
import com.ecommerce.common.model.enums.StoreStatus;
import com.ecommerce.store.domain.store.converter.StoreConverter;
import com.ecommerce.store.entity.StoreEntity;
import com.ecommerce.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@ProcessingGroup("stores")
@Slf4j
@Component
@RequiredArgsConstructor
public class StoreProjection {
    private final StoreRepository storeRepository;
    private final StoreConverter storeConverter;

    @ResetHandler
    private void resetStoreModel() {
        log.info("reset Store Model triggered");
        storeRepository.deleteAll();
    }

    @AllowReplay
    @EventHandler
    public void on(StoreCreatedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 상점 생성 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        StoreEntity entity = storeConverter.toEntity(event);
        entity.setStatus(StoreStatus.REGISTERED);
        entity.setStar(0);
        storeRepository.save(entity);
    }

    @AllowReplay
    @EventHandler
    public void on(StoreUpdatedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 상점 수정 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        storeRepository.updateStoreByEvent(
                event.getStoreId(),
                event.getName(),
                event.getAddress(),
                event.getCategory(),
                event.getStar(),
                event.getThumbnailUrl(),
                event.getPhoneNumber(),
                event.getUpdatedAt()
        );
    }

    @AllowReplay
    @EventHandler
    public void on(StoreDeletedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 상점 삭제 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        storeRepository.deleteStoreByEvent(
                event.getStoreId(),
                StoreStatus.UNREGISTERED,
                event.getUnregisteredAt()
        );
    }
}
