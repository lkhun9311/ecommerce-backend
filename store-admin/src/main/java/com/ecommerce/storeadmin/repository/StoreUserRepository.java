package com.ecommerce.storeadmin.repository;

import com.ecommerce.storeadmin.entity.StoreUserEntity;
import com.ecommerce.storeadmin.entity.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus status);
}
