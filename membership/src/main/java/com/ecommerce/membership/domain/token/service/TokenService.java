package com.ecommerce.membership.domain.token.service;

import com.ecommerce.db.user.enums.UserStatus;
import com.ecommerce.membership.common.error.ErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.token.controller.model.UserClaim;
import com.ecommerce.membership.domain.token.ifs.TokenHelperIfs;
import com.ecommerce.membership.domain.token.model.TokenDto;
import com.ecommerce.membership.domain.user.model.UserDto;
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
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_STATUS = "status";
    private static final String USER_ADDRESS = "address";

    /**
     * userID를 기반으로 accessToken 발급
     *
     * @param userClaim 사용자의 Claim 정보
     * @return accessToken DTO 객체
     */
    public TokenDto issueAccessToken(UserClaim userClaim) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userClaim.getUserId());
        data.put(USER_NAME, userClaim.getName());
        data.put(USER_EMAIL, userClaim.getEmail());
        data.put(USER_STATUS, userClaim.getStatus());
        data.put(USER_ADDRESS, userClaim.getAddress());
        return tokenHelperIfs.issueAccessToken(data);
    }

    /**
     * userID를 기반으로 refreshToken 발급
     *
     * @param userClaim 사용자의 Claim 정보
     * @return refreshToken DTO 객체
     */
    public TokenDto issueRefreshToken(UserClaim userClaim) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userClaim.getUserId());
        data.put(USER_NAME, userClaim.getName());
        data.put(USER_EMAIL, userClaim.getEmail());
        data.put(USER_STATUS, userClaim.getStatus());
        data.put(USER_ADDRESS, userClaim.getAddress());
        return tokenHelperIfs.issueRefreshToken(data);
    }

    /**
     * 주어진 토큰을 검증하고 해당하는 userID 반환
     *
     * @param token 검증할 토큰
     * @return userId 사용자의 ID
     * @throws ApiException 토큰이 유효하지 않거나 만료되었을 때 발생하는 예외
     */
    public UserDto validateToken(String token) {
        Map<String, Object> map = tokenHelperIfs.validateTokenWithThrow(token);
        Object userIdObject = map.get(USER_ID);
        Objects.requireNonNull(userIdObject, () -> {
            throw new ApiException(ErrorCode.NULL_POINT_ERROR);
        });

        return UserDto.builder()
                .userId(Long.parseLong(userIdObject.toString()))
                .name(map.get(USER_NAME).toString())
                .email(map.get(USER_EMAIL).toString())
                .status(UserStatus.valueOf(map.get(USER_STATUS).toString()))
                .address(map.get(USER_ADDRESS).toString())
                .build();
    }
}
