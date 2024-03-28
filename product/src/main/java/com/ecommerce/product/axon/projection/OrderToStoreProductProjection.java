package com.ecommerce.product.axon.projection;

import com.ecommerce.common.axon.query.storeproduct.StoreProductQuery;
import com.ecommerce.common.model.storeproduct.StoreProductQueryResponse;
import com.ecommerce.product.domain.storeproduct.converter.StoreProductConverter;
import com.ecommerce.product.domain.storeproduct.service.StoreProductService;
import com.ecommerce.product.entity.StoreProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderToStoreProductProjection {
    private final StoreProductService storeProductService;
    private final StoreProductConverter storeProductConverter;

    // StoreProductQuery를 처리하는 QueryHandler
    @QueryHandler
    public List<StoreProductQueryResponse> on(StoreProductQuery query){
        log.info("handling {}", query);

        // StoreProductQuery에 포함된 storeProductIdList를 이용해 storeProductEntityList 조회
        List<StoreProductEntity> storeProductEntityList = query.getStoreProductIdList().stream()
                .map(storeProductService::getStoreProductWithThrow)
                .collect(Collectors.toList());

        // storeProductEntityList를 List<StoreProductQueryResponse>로 변환하고 반환
        return storeProductConverter.toQueryResponse(storeProductEntityList);
    }
}
