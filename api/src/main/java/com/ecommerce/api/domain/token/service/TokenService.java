package com.ecommerce.api.domain.token.service;

import com.ecommerce.api.common.error.ErrorCode;
import com.ecommerce.api.common.exception.ApiException;
import com.ecommerce.api.domain.token.ifs.TokenHelperIfs;
import com.ecommerce.api.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 토큰 관련 도메인 로직을 처리하는 서비스
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    private static final String USER_ID = "userId";

    /**
     * userID를 기반으로 accessToken 발급
     *
     * @param userId 사용자의 ID
     * @return accessToken DTO 객체
     */
    public TokenDto issueAccessToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    /**
     * userID를 기반으로 refreshToken 발급
     *
     * @param userId 사용자의 ID
     * @return refreshToken DTO 객체
     */
    public TokenDto issueRefreshToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    /**
     * 주어진 토큰을 검증하고 해당하는 userID 반환
     *
     * @param token 검증할 토큰
     * @return userId 사용자의 ID
     * @throws ApiException 토큰이 유효하지 않거나 만료되었을 때 발생하는 예외
     */
    public Long validateToken(String token) {
        Map<String, Object> map = tokenHelperIfs.validateTokenWithThrow(token);
        Object userId = map.get(USER_ID);
        Objects.requireNonNull(userId, () -> {
            throw new ApiException(ErrorCode.NULL_POINT_ERROR);
        });
        return Long.parseLong(userId.toString());
    }
}
