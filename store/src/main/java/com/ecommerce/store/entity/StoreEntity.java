package com.ecommerce.store.entity;

import com.ecommerce.store.entity.enums.StoreCategory;
import com.ecommerce.store.entity.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "store")
@Data // 클래스 내의 필드에 대한 getter, setter, toString, equals, hashCode 메서드 자동 생성
@EqualsAndHashCode(callSuper = true) // 상속한 부모(BaseEntity)의 필드도 equals와 hashCode에서 고려
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoreEntity extends BaseEntity {

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

    private double star; // primitive double 기본적으로 0 셋팅

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Column(length = 20)
    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime updatedAt;
}
