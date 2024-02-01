package com.ecommerce.api.domain.token.business;

import com.ecommerce.api.common.annotation.Business;
import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.token.controller.model.TokenResponse;
import com.ecommerce.api.domain.token.converter.TokenConverter;
import com.ecommerce.api.domain.token.model.TokenDto;
import com.ecommerce.api.domain.token.service.TokenService;
import com.ecommerce.db.user.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * 토큰 발급, 인증 비즈니스 로직
 */
@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. userEntity에서 userId 추출
     * 2. accessToken, refreshToken 발행
     * 3. TokenConverter를 통해 TokenResponse로 변환
     *
     * @param userEntity 토큰을 발급할 대상 userEntity
     * @return TokenResponse 객체
     * @throws ApiException userEntity가 null일 경우 발생하는 예외
     */
    public TokenResponse issueToken(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(ue -> ue.getId()) // 1. userEntity에서 userId 추출
                .map(userId -> {
                    // 2. accessToken, refreshToken 발행
                    TokenDto accessToken = tokenService.issueAccessToken(userId);
                    TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                    // 3. TokenConverter를 통해 TokenResponse로 변환
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(
                        () -> new ApiException(ErrorCode.NULL_POINT_ERROR)
                );
    }

    /**
     * AccessToken의 유효성을 검사하는 메소드입니다.
     *
     * @param accessToken 검사할 AccessToken
     * @return 유효한 경우 사용자의 ID, 그렇지 않은 경우 null 반환
     */
    public Long validateAccessToken(String accessToken) {
        return tokenService.validateToken(accessToken);
    }
}
