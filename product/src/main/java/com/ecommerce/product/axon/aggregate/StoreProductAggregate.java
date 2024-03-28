package com.ecommerce.product.axon.aggregate;

import com.ecommerce.common.axon.event.storeproduct.StoreProductCreatedEvent;
import com.ecommerce.common.axon.event.storeproduct.StoreProductDeletedEvent;
import com.ecommerce.common.axon.event.storeproduct.StoreProductUpdatedEvent;
import com.ecommerce.common.model.enums.StoreProductStatus;
import com.ecommerce.product.axon.command.CreateStoreProductCommand;
import com.ecommerce.product.axon.command.DeleteStoreProductCommand;
import com.ecommerce.product.axon.command.UpdateStoreProductCommand;
import com.ecommerce.product.common.error.StoreProductErrorCode;
import com.ecommerce.product.common.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.Objects;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Aggregate(snapshotTriggerDefinition = "storeProductSnapshotTriggerDefinition", cache = "storeProductCache")
@EqualsAndHashCode
public class StoreProductAggregate {
    @AggregateIdentifier
    private String storeProductId;
    private String storeId;
    private String name;
    private BigDecimal amount;
    private StoreProductStatus status;
    private String thumbnailUrl;
    private int likeCount;
    private String color;

    @CommandHandler
    public StoreProductAggregate(CreateStoreProductCommand command) {
        log.info("handling {}", command);
        apply(new StoreProductCreatedEvent(
                command.getStoreProductId(),
                command.getStoreId(),
                command.getName(),
                command.getAmount(),
                command.getThumbnailUrl(),
                command.getColor()
                )
        );
    }

    @EventSourcingHandler
    public void on(StoreProductCreatedEvent event) {
        log.info("handling {}", event);
        this.storeProductId = event.getStoreProductId();
        this.storeId = event.getStoreId();
        this.name = event.getName();
        this.amount = event.getAmount();
        this.status = StoreProductStatus.REGISTERED;
        this.thumbnailUrl = event.getThumbnailUrl();
        this.likeCount = 0;
        this.color = event.getColor();
    }

    @CommandHandler
    public void handle(UpdateStoreProductCommand command) {
        log.info("handling {}", command);
        if (!Objects.equals(this.storeProductId, command.getStoreProductId())) {
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_NOT_FOUND);
        }

        if (!Objects.equals(this.storeId, command.getStoreId())) {
            throw new ApiException(StoreProductErrorCode.STORE_NOT_FOUND);
        }

        if (!Objects.equals(this.status, StoreProductStatus.UNREGISTERED)) {
            apply(new StoreProductUpdatedEvent(command.getStoreProductId(), command.getStoreId(), command.getName(), command.getAmount(), command.getThumbnailUrl()));
        } else {
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_NOT_FOUND, "해당 상품이 이미 삭제되었습니다.");
        }
    }

    @EventSourcingHandler
    public void on(StoreProductUpdatedEvent event) {
        log.info("handling {}", event);
        this.storeId = event.getStoreId();
        this.name = event.getName();
        this.amount = event.getAmount();
        this.thumbnailUrl = event.getThumbnailUrl();
    }

    @CommandHandler
    public void handle(DeleteStoreProductCommand command) {
        log.info("handling {}", command);

        if (!Objects.equals(this.storeProductId, command.getStoreProductId())) {
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_NOT_FOUND);
        }

        if (!Objects.equals(this.status, StoreProductStatus.UNREGISTERED)) {
            apply(new StoreProductDeletedEvent(command.getStoreProductId()));
        } else {
            throw new ApiException(StoreProductErrorCode.STORE_PRODUCT_NOT_FOUND, "해당 상품이 이미 삭제되었습니다.");
        }
    }

    @EventSourcingHandler
    public void on(StoreProductDeletedEvent event) {
        log.info("handling {}", event);
        this.status = StoreProductStatus.UNREGISTERED;
    }
}
