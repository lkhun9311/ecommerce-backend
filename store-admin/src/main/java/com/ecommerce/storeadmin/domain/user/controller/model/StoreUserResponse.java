package com.ecommerce.storeadmin.domain.user.controller.model;

import com.ecommerce.db.storeuser.enums.StoreUserRole;
import com.ecommerce.db.storeuser.enums.StoreUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreUserResponse {

    private UserResponse user;

    private StoreResponse store;

    /**
     * static class
     * 코드의 구조화와 가독성 향상
     * StoreUserResponse 클래스의 구성원임을 명시(내부 클래스, inner class)
     * 외부로부터 직접적인 접근 차단(캡슐화)
     * 외부에 공개할 필요가 없는 내부적인 세부사항으로 정보 은닉(캡슐화)
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse{

        private Long id;

        private String email;

        private StoreUserStatus status;

        private StoreUserRole role;

        private LocalDateTime registeredAt;

        private LocalDateTime unregisteredAt;

        private LocalDateTime updatedAt;

        private LocalDateTime lastLoginAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public  static class StoreResponse{

        private Long id;

        private String name;
    }
}
