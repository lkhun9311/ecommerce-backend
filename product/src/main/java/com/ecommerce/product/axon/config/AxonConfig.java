package com.ecommerce.product.axon.config;

import com.ecommerce.product.axon.aggregate.StoreProductAggregate;
import com.ecommerce.product.axon.version.StoreProductCreatedEventVersion1;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.serialization.upcasting.event.EventUpcasterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    // JacksonSerializer를 사용해 메시지 직렬화/역직렬화 빈 생성
    @Bean
    public Serializer messageSerializer() {
        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // 알려지지 않은 속성에 대해 실패를 허용하지 않도록 설정
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 모든 클래스의 정보를 유지하도록 설정
        // Jackson 라이브러리 중 하나인 ObjectMapper는 객체를 직렬화할 때 클래스 정보를 포함 하지 않음
        // 보안 및 안정성 이유로 기본적으로 비활성화
        // 하지만 Axon Framework는 클래스 정보를 사용해 객체를 역직렬화하고 클래스의 type을 식별하기 위해 클래스 정보를 알아야 함
        objectMapper.activateDefaultTyping( // ObjectMapper 기본 타입 활성화
                objectMapper.getPolymorphicTypeValidator(), // Jackson이 객체의 클래스 정보를 보존하고 이를 역직렬화할 때 사용
                ObjectMapper.DefaultTyping.NON_FINAL // 최상위 클래스가 final로 선언되어 있지 않은 경우에만 JSON에 클래스 type 정보 포함
                                                     // 만약 최상위 클래스가 final로 선언되어 있을 경우 Jackson은 클래스 type 정보를 포함하지 않음
        );

        // JacksonSerializer 설정
        return JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .build();
    }

    @Bean
    public AggregateFactory<StoreProductAggregate> aggregateFactory(){
        return new GenericAggregateFactory<>(StoreProductAggregate.class);
    }

    @Bean
    public Snapshotter snapshotter(EventStore eventStore, TransactionManager transactionManager){
        return AggregateSnapshotter
                .builder()
                .eventStore(eventStore)
                .aggregateFactories(aggregateFactory())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public SnapshotTriggerDefinition storeProductSnapshotTriggerDefinition(EventStore eventStore, TransactionManager transactionManager){
        final int SNAPSHOT_THRESHOLD = 5;
        return new EventCountSnapshotTriggerDefinition(snapshotter(eventStore,transactionManager),SNAPSHOT_THRESHOLD);
    }

    @Bean
    public Cache storeProductCache(){
        return new WeakReferenceCache();
    }

    @Bean
    public Repository<StoreProductAggregate> storeProductAggregateRepositoryAggregateRepository(EventStore eventStore, SnapshotTriggerDefinition snapshotTriggerDefinition){
        return EventSourcingRepository
                .builder(StoreProductAggregate.class)
                .eventStore(eventStore)
                .snapshotTriggerDefinition(snapshotTriggerDefinition)
                .cache(storeProductCache())
                .build();
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerTrackingEventProcessor(
                "store_products",
                org.axonframework.config.Configuration::eventStore,
                c -> TrackingEventProcessorConfiguration.forParallelProcessing(3)
                        .andBatchSize(500)
        );

        configurer.registerSequencingPolicy("store_products",
                configuration -> SequentialPerAggregatePolicy.instance());
    }

    @Bean
    public EventUpcasterChain eventUpcasterChain(){
        // StoreProductCreatedEventVersion1을 포함한 EventUpcasterChain을 생성해 반환합니다.
        // EventUpcaster를 연결하고 이벤트 버전 간 변환 수행
        return new EventUpcasterChain(
                new StoreProductCreatedEventVersion1()
        );
    }
}