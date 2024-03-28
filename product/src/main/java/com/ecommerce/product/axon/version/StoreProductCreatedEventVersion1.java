package com.ecommerce.product.axon.version;

import com.ecommerce.common.axon.event.storeproduct.StoreProductCreatedEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

@Slf4j
public class StoreProductCreatedEventVersion1 extends SingleEventUpcaster {
    // 업캐스팅할 대상 이벤트의 SerializedType 지정
    private static SimpleSerializedType targetType = new SimpleSerializedType(StoreProductCreatedEvent.class.getTypeName(), null);

    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        // 대상 이벤트의 유형과 일치하는지 확인해 업캐스팅 가능 여부 반환
        return intermediateRepresentation.getType().equals(targetType);
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(
            IntermediateEventRepresentation intermediateRepresentation
    ) {
        // 이벤트 업캐스팅 수행
        return intermediateRepresentation.upcastPayload(
                // 대상 이벤트의 SerializedType과 Version 지정
                new SimpleSerializedType(targetType.getName(), "1.0"),
                // 새로운 Payload의 유형을 JsonNode로 지정
                com.fasterxml.jackson.databind.JsonNode.class,
                // 이벤트 업캐스팅 로직
                event -> {
                    log.info("event의 유형: {}", event.getNodeType().name());
                    log.info("event : {}", event);

                    // JsonNode가 ObjectNode인지 확인
                    if (event instanceof ObjectNode) {
                        ObjectNode objectNode = (ObjectNode) event;
                        // 'color' 필드 추가
                        objectNode.put("color", "N/A");
                        return objectNode;
                        // JsonNode가 ArrayNode인지 확인
                    } else if (event instanceof ArrayNode) {
                        ArrayNode arrayNode = (ArrayNode) event;
                        // 배열의 각 요소에 대해 'color' 필드 추가
                        for (JsonNode node : arrayNode) {
                            if (node instanceof ObjectNode) {
                                log.info("node의 유형: {}", node.getNodeType().name());
                                log.info("node : {}", node);

                                ObjectNode objNode = (ObjectNode) node;
                                log.info("objNode의 유형: {}", node.getNodeType().name());
                                log.info("objNode : {}", node);
                                // 'color' 필드 추가
                                objNode.put("color", "N/A");
                            }
                        }
                        return arrayNode;
                    } else {
                        // ObjectNode나 ArrayNode가 아닌 경우 에러 처리
                        log.error("JsonNode가 ObjectNode나 ArrayNode가 아니므로 업캐스팅을 수행할 수 없습니다.");
                        throw new IllegalArgumentException("Event is not an ObjectNode or ArrayNode, cannot perform the upcasting.");
                    }
                }
        );
    }
}
