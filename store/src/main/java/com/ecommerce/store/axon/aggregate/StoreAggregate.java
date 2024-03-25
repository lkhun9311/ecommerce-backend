package com.ecommerce.store.axon.aggregate;

import com.ecommerce.common.axon.event.store.StoreCreatedEvent;
import com.ecommerce.common.axon.event.store.StoreDeletedEvent;
import com.ecommerce.common.axon.event.store.StoreUpdatedEvent;
import com.ecommerce.common.model.enums.StoreProductStatus;
import com.ecommerce.store.axon.command.CreateStoreCommand;
import com.ecommerce.store.axon.command.DeleteStoreCommand;
import com.ecommerce.store.axon.command.UpdateStoreCommand;
import com.ecommerce.store.common.error.StoreErrorCode;
import com.ecommerce.store.common.exception.ApiException;
import com.ecommerce.common.model.enums.StoreCategory;
import com.ecommerce.common.model.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Aggregate(snapshotTriggerDefinition = "storeSnapshotTriggerDefinition", cache = "storeCache")
@EqualsAndHashCode
public class StoreAggregate {
    @AggregateIdentifier
    private String storeId;
    private String name;
    private String address;
    private StoreStatus status;
    private StoreCategory category;
    private double star;
    private String thumbnailUrl;
    private String phoneNumber;
    private Boolean isDoubleChecked;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime updatedAt;

    @CommandHandler
    public StoreAggregate(CreateStoreCommand command) {
        log.info("handling {}", command);

        if (!Objects.equals(Boolean.TRUE, command.getIsDoubleChecked())) {
            throw new ApiException(StoreErrorCode.STORE_NAME_DOUBLED);
        }

        apply(new StoreCreatedEvent(
                command.getStoreId(),
                command.getName(),
                command.getAddress(),
                command.getCategory(),
                command.getThumbnailUrl(),
                command.getPhoneNumber(),
                Boolean.TRUE,
                command.getRegisteredAt()
                )
        );
    }

    @EventSourcingHandler
    public void on(StoreCreatedEvent event) {
        log.info("handling {}", event);
        this.storeId = event.getStoreId();
        this.name = event.getName();
        this.address = event.getAddress();
        this.status = StoreStatus.REGISTERED;
        this.category = event.getCategory();
        this.star = 0;
        this.thumbnailUrl = event.getThumbnailUrl();
        this.phoneNumber = event.getPhoneNumber();
        this.isDoubleChecked = event.getIsDoubleChecked();
        this.registeredAt = event.getRegisteredAt();
    }

    @CommandHandler
    public void handle(UpdateStoreCommand command) {
        log.info("handling {}", command);
        if (!Objects.equals(this.storeId, command.getStoreId())) {
            throw new ApiException(StoreErrorCode.STORE_NOT_FOUND);
        }

        if (!Objects.equals(Boolean.TRUE, command.getIsDoubleChecked())) {
            throw new ApiException(StoreErrorCode.STORE_NAME_DOUBLED);
        }

        if (!Objects.equals(this.status, StoreProductStatus.UNREGISTERED)) {
            apply(new StoreUpdatedEvent(
                    command.getStoreId(),
                    command.getName(),
                    command.getAddress(),
                    command.getCategory(),
                    command.getStar(),
                    command.getThumbnailUrl(),
                    command.getPhoneNumber(),
                    command.getIsDoubleChecked(),
                    command.getUpdatedAt()
                    )
            );
        } else {
            throw new ApiException(StoreErrorCode.STORE_NOT_FOUND, "해당 상점이 이미 삭제되었습니다.");
        }
    }

    @EventSourcingHandler
    public void on(StoreUpdatedEvent event) {
        log.info("handling {}", event);
        this.name = event.getName();
        this.address = event.getAddress();
        this.category = event.getCategory();
        this.star = event.getStar();
        this.thumbnailUrl = event.getThumbnailUrl();
        this.phoneNumber = event.getPhoneNumber();
        this.isDoubleChecked = event.getIsDoubleChecked();
        this.updatedAt = event.getUpdatedAt();
    }

    @CommandHandler
    public void handle(DeleteStoreCommand command) {
        log.info("handling {}", command);

        if (!Objects.equals(this.storeId, command.getStoreId())) {
            throw new ApiException(StoreErrorCode.STORE_NOT_FOUND);
        }

        if (!Objects.equals(this.status, StoreStatus.UNREGISTERED)) {
            apply(new StoreDeletedEvent(
                    command.getStoreId(),
                    command.getUnregisteredAt()
                    )
            );
        } else {
            throw new ApiException(StoreErrorCode.STORE_NOT_FOUND, "해당 상점이 이미 삭제되었습니다.");
        }
    }

    @EventSourcingHandler
    public void on(StoreDeletedEvent event) {
        log.info("handling {}", event);
        this.status = StoreStatus.UNREGISTERED;
        this.unregisteredAt = event.getUnregisteredAt();
    }
}
