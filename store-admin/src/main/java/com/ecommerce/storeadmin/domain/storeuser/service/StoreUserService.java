package com.ecommerce.storeadmin.domain.storeuser.service;

import com.ecommerce.db.storeuser.StoreUserEntity;
import com.ecommerce.db.storeuser.StoreUserRepository;
import com.ecommerce.db.storeuser.enums.StoreUserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 상점 사용자 등록
     *
     * @param storeUserEntity 등록할 상점 사용자 엔티티
     * @return 등록된 상점 사용자 엔티티
     */
    public StoreUserEntity register(StoreUserEntity storeUserEntity) {
        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));
        storeUserEntity.setRegisteredAt(LocalDateTime.now());
        return storeUserRepository.save(storeUserEntity);
    }

    /**
     * 특정 이메일로 등록된 상점 사용자 조회
     *
     * @param email 조회할 상점 사용자의 이메일
     * @return 조회된 상점 사용자 엔티티 (존재하지 않을 경우 Optional.empty())
     */
    public Optional<StoreUserEntity> getRegisteredUser(String email) {
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);
    }
}
