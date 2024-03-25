package com.ecommerce.store.entity;

import com.ecommerce.common.model.enums.StoreCategory;
import com.ecommerce.common.model.enums.StoreStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "store")
@Data // 클래스 내의 필드에 대한 getter, setter, toString, equals, hashCode 메서드 자동 생성
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreEntity {
    @Id
    @Column(length = 100, nullable = false)
    private String storeId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    private double star; // primitive double, 기본적으로 0을 셋팅

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean isDoubleChecked;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime updatedAt;
}
