package com.ecommerce.order.domain.userorder.service;

import com.ecommerce.common.axon.query.StoreProductQuery;
import com.ecommerce.common.model.StoreProductQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreProductQueryServiceImpl implements StoreProductQueryService {
    private final QueryGateway queryGateway;

    /**
     * 지정된 상품 ID 목록에 대한 상품 정보를 비동기적으로 조회
     *
     * @param storeProductIdList 가져올 상품의 ID 목록
     * @return CompletableFuture를 통해 처리된 상품 정보 목록
     */
    @Override
    public CompletableFuture<List<StoreProductQueryResponse>> getStoreProductInfo(List<String> storeProductIdList) {
        StoreProductQuery storeProductQuery = new StoreProductQuery(storeProductIdList);
        log.info("handling {}", storeProductQuery);

        // storeProductQuery 객체를 보내고 응답을 변환해 CompletableFuture로 반환
        return queryGateway.query(storeProductQuery, ResponseTypes.multipleInstancesOf(StoreProductQueryResponse.class))
                // 예외가 발생하면 처리하고 CompletableFuture에 예외를 포함해 반환
                .exceptionally(ex -> {
                    log.error("상품 정보를 가져오는 도중 오류가 발생했습니다: {}", ex.getMessage());
                    throw new IllegalArgumentException("상품 정보를 가져오는 데 실패했습니다", ex);
                });
    }
}
