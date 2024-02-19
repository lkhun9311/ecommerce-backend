package com.ecommerce.membership.domain.user.service;

import com.ecommerce.db.user.UserEntity;
import com.ecommerce.db.user.UserRepository;
import com.ecommerce.db.user.enums.UserStatus;
import com.ecommerce.membership.common.error.ErrorCode;
import com.ecommerce.membership.common.error.UserErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.user.model.User;
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
                    it.setStatus(UserStatus.REGISTERED);
                    it.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(it);
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
        Optional<UserEntity> userEntity = userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email,
                password,
                UserStatus.REGISTERED
        );
        return userEntity.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * userId로 등록된 사용자 엔티티를 찾아오는 메소드
     *
     * @param userId 사용자 ID
     * @return 찾아온 사용자 엔티티
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public UserEntity getUserWithThrow(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId,
                UserStatus.REGISTERED);
        return userEntity.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 현재 로그인된 사용자 정보를 조회하는 메소드
     *
     * @param user 현재 로그인된 사용자
     * @return 현재 로그인된 사용자 정보
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    public UserEntity me(User user) {
        Optional<UserEntity> userEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(
                user.getId(),
                user.getStatus());
        return userEntity.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
