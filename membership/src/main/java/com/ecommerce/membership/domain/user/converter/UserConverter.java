package com.ecommerce.membership.domain.user.converter;

import com.ecommerce.common.axon.event.membership.UserCreatedEvent;
import com.ecommerce.membership.common.annotation.Converter;
import com.ecommerce.membership.common.error.ErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.user.controller.model.UserCreateRequest;
import com.ecommerce.membership.domain.user.controller.model.UserResponse;
import com.ecommerce.membership.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    /**
     * UserCreatedEvent를 UserEntity로 변환
     *
     * @param event 회원가입할 사용자 정보를 담은 이벤트 객체
     * @return 회원가입을 위한 UserEntity
     * @throws ApiException UserCreatedEvent가 null인 경우 예외 발생
     */
    public UserEntity toEntity(UserCreatedEvent event) {

        return Optional.ofNullable(event)
                .map(it ->
                        // to entity
                        UserEntity.builder()
                                .userId(it.getUserId())
                                .name(it.getName())
                                .email(it.getEmail())
                                .password(it.getPassword())
                                .address(it.getAddress())
                                .thumbnailUrl(it.getThumbnailUrl())
                                .phoneNumber(it.getPhoneNumber())
                                .isDoubleChecked(it.getIsDoubleChecked())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "UserRegisterRequest Null"));
    }

    /**
     * UserEntity를 UserResponse로 변환
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
                                .userId(it.getUserId())
                                .name(it.getName())
                                .email(it.getEmail())
                                .status(it.getStatus())
                                .address(it.getAddress())
                                .thumbnailUrl(it.getThumbnailUrl())
                                .phoneNumber(it.getPhoneNumber())
                                .isDoubleChecked(it.getIsDoubleChecked())
                                .registeredAt(it.getRegisteredAt())
                                .unregisteredAt(it.getUnregisteredAt())
                                .lastLoginAt(it.getLastLoginAt())
                                .updatedAt(it.getUpdatedAt())
                                .build()
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "newEntity Null"));
    }
}
