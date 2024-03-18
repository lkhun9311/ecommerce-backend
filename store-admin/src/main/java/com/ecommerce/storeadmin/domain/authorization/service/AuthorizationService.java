package com.ecommerce.storeadmin.domain.authorization.service;

import com.ecommerce.storeadmin.common.error.ErrorCode;
import com.ecommerce.storeadmin.common.exception.ApiException;
import com.ecommerce.storeadmin.domain.authorization.model.UserSession;
import com.ecommerce.storeadmin.domain.storeuser.service.StoreUserService;
import com.ecommerce.storeadmin.entity.StoreUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자 권한과 인증을 관리하는 서비스 클래스
 * Spring Security의 UserDetailsService 구현
 * 사용자 정보를 조회하고 UserDetails 객체로 반환
 */
@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
//    private final StoreRepository storeRepository;

    /**
     * Spring Security의 UserDetailsService 구현
     * 주어진 사용자 이름(username)에 대한 정보를 조회하고 UserDetails 객체로 반환
     *
     * @param username 사용자 이름
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 주어진 사용자 이름에 대한 정보가 없을 경우 발생하는 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 주어진 사용자 이름(username)으로 등록된 상점의 사용자 정보 조회
        StoreUserEntity storeUserEntity = storeUserService.getRegisteredUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // 해당 가게 사용자가 속한 상점 정보 조회
//        StoreEntity storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
//                storeUserEntity.getStoreId(),
//                StoreStatus.REGISTERED
//        ).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));

        // UserDetails 객체를 생성하고 반환
        return UserSession.builder()
                .userId(storeUserEntity.getId())
                .email(storeUserEntity.getEmail())
                .password(storeUserEntity.getPassword())
//                .status(storeUserEntity.getStatus())
                .role(storeUserEntity.getRole())
                .registeredAt(storeUserEntity.getRegisteredAt())
                .unregisteredAt(storeUserEntity.getUnregisteredAt())
                .lastLoginAt(storeUserEntity.getLastLoginAt())
//                .storeId(storeEntity.getId())
//                .storeName(storeEntity.getName())
                .build();
    }
}

