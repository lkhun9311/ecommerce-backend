package com.ecommerce.membership.repository;

import com.ecommerce.membership.entity.UserEntity;
import com.ecommerce.common.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByUserIdDesc(String email, String password, UserStatus status);

    Optional<UserEntity> findFirstByUserIdAndStatusOrderByUserIdDesc(String userId, UserStatus status);

    Optional<UserEntity> findFirstByEmailAndStatusOrderByUserIdDesc(String email, UserStatus userStatus);

    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.name = :name, u.email = :email, u.password = :password, u.address = :address, u.thumbnailUrl = :thumbnailUrl, u.phoneNumber = :phoneNumber, u.isDoubleChecked = :isDoubleChecked, u.updatedAt = :updatedAt " +
            "WHERE u.userId = :userId"
    )
    void updateUserByEvent(
            @Param("userId") String userId,
            @Param("name") String name,
            @Param("email") String email,
            @Param("password") String password,
            @Param("address") String address,
            @Param("thumbnailUrl") String thumbnailUrl,
            @Param("phoneNumber") String phoneNumber,
            @Param("isDoubleChecked") Boolean isDoubleChecked,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.status = :status, u.unregisteredAt = :unregisteredAt " +
            "WHERE u.userId = :userId"
    )
    void deleteUserByEvent(
            @Param("userId") String userId,
            @Param("status") UserStatus status,
            @Param("unregisteredAt") LocalDateTime unregisteredAt
    );

    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.lastLoginAt = :lastLoginAt " +
            "WHERE u.userId = :userId"
    )
    void updateUserLastLoginAtByEvent(
            @Param("userId") String userId,
            @Param("lastLoginAt") LocalDateTime lastLoginAt
    );
}
