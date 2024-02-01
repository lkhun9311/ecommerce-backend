package com.ecommerce.api.domain.user.service;

import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.error.UserErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.db.user.UserEntity;
import com.ecommerce.db.user.UserRepository;
import com.ecommerce.db.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리하는 서비스
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입 로직
     *
     * @param userEntity 등록할 사용자 엔티티
     * @return 등록된 사용자 엔티티
     * @throws ApiException userEntity가 null인 경우 예외 발생
     */
    @Transactional
    public UserEntity register(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "UserEntity Null"));
    }

    /**
     * 로그인 로직
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @return 로그인한 사용자 엔티티
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public UserEntity login(String email, String password) {
        return getUserWithThrow(email, password);
    }

    /**
     * 이메일과 비밀번호로 사용자 엔티티를 찾아오는 메소드
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @return 찾아온 사용자 엔티티
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public UserEntity getUserWithThrow(String email, String password) {
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email,
                password,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * userId로 등록된 사용자 엔티티를 찾아오는 메소드
     *
     * @param userId 사용자 ID
     * @return 찾아온 사용자 엔티티
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public UserEntity getUserWithThrow(Long userId) {
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
