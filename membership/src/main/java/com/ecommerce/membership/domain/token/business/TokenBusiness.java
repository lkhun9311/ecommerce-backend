package com.ecommerce.membership.domain.token.business;

import com.ecommerce.membership.common.annotation.Business;
import com.ecommerce.membership.common.error.ErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.token.controller.model.TokenResponse;
import com.ecommerce.membership.domain.token.controller.model.TokenValidateResponse;
import com.ecommerce.membership.domain.token.converter.TokenConverter;
import com.ecommerce.membership.domain.token.model.TokenDto;
import com.ecommerce.membership.domain.token.service.TokenService;
import com.ecommerce.membership.domain.user.model.UserDto;
import com.ecommerce.membership.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

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
    @Cacheable(cacheNames = "JwtToken", key = "#userEntity.userId")
    public TokenResponse issueToken(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(tokenConverter::toClaim) // 1. userEntity에서 userId 추출
                .map(userClaim -> {
                    // 2. accessToken, refreshToken 발행
                    TokenDto accessToken = tokenService.issueAccessToken(userClaim);
                    TokenDto refreshToken = tokenService.issueRefreshToken(userClaim);
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
    public TokenValidateResponse validateAccessToken(String accessToken) {
        UserDto userDto = tokenService.validateToken(accessToken);
        return TokenValidateResponse.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .status(userDto.getStatus())
                .address(userDto.getAddress())
                .build();
    }
}
