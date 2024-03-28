package com.ecommerce.store.axon.projection;

import com.ecommerce.common.axon.query.store.StoreIdQuery;
import com.ecommerce.store.domain.store.service.StoreService;
import com.ecommerce.store.entity.StoreEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreProductToStoreProjection {
    private final StoreService storeService;

    @QueryHandler
    public Boolean on(StoreIdQuery query){
        log.info("handling {}", query);
        Optional<StoreEntity> entity = storeService.getStoreWithoutThrow(query.getStoreId());
        log.info("result {}", entity);
        return entity.isPresent();
    }
}
