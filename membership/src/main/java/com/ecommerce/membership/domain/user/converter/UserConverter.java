package com.ecommerce.membership.domain.user.converter;

import com.ecommerce.db.user.UserEntity;
import com.ecommerce.membership.common.annotation.Converter;
import com.ecommerce.membership.common.error.ErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.user.controller.model.UserRegisterRequest;
import com.ecommerce.membership.domain.user.controller.model.UserResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    /**
     * UserRegisterRequest를 UserEntity로 변환하는 메소드
     *
     * @param request 회원가입 요청 정보
     * @return 회원가입을 위한 UserEntity
     * @throws ApiException UserRegisterRequest가 null인 경우 예외 발생
     */
    public UserEntity toEntity(UserRegisterRequest request) {

        return Optional.ofNullable(request)
                .map(it ->
                        // to entity
                        UserEntity.builder()
                                .name(it.getName())
                                .email(it.getEmail())
                                .password(it.getPassword())
                                .address(it.getAddress())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "UserRegisterRequest Null"));
    }

    /**
     * UserEntity를 UserResponse로 변환하는 메소드
     *
     * @param newEntity 회원가입 또는 조회된 UserEntity
     * @return UserResponse
     * @throws ApiException UserEntity가 null인 경우 예외 발생
     */
    public UserResponse toResponse(UserEntity newEntity) {

        return Optional.ofNullable(newEntity)
                .map(it ->
                        // to response
                        UserResponse.builder()
                                .id(it.getId())
                                .name(it.getName())
                                .email(it.getEmail())
                                .status(it.getStatus())
                                .address(it.getAddress())
                                .registeredAt(it.getRegisteredAt())
                                .unregisteredAt(it.getUnregisteredAt())
                                .lastLoginAt(it.getLastLoginAt())
                                .updatedAt(it.getUpdatedAt())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "newEntity Null"));
    }
}
