package com.ecommerce.api.domain.token.converter;

import com.ecommerce.api.common.annotation.Converter;
import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.token.controller.model.TokenResponse;
import com.ecommerce.api.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 토큰 관련 DTO 객체를 클라이언트에 반환할 응답 객체로 변환
 */
@RequiredArgsConstructor
@Converter
public class TokenConverter {

    /**
     * accessToken과 refreshToken을 클라이언트에 반환할 응답 객체로 변환
     *
     * @param accessToken  accessToken DTO 객체
     * @param refreshToken refreshToken DTO 객체
     * @return TokenResponse(토큰 응답 객체)
     * @throws ApiException accessToken 또는 refreshToken이 null인 경우 발생하는 예외
     */
    public TokenResponse toResponse(
            TokenDto accessToken,
            TokenDto refreshToken
    ) {

        Objects.requireNonNull(accessToken, () -> {
            throw new ApiException(ErrorCode.NULL_POINT_ERROR);
        });
        Objects.requireNonNull(refreshToken, () -> {
            throw new ApiException(ErrorCode.NULL_POINT_ERROR);
        });

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }
}
