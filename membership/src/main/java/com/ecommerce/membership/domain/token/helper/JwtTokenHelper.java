package com.ecommerce.membership.domain.token.helper;

import com.ecommerce.membership.common.error.TokenErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.token.ifs.TokenHelperIfs;
import com.ecommerce.membership.domain.token.model.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 토큰을 생성하고 검증
 */
@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    /**
     * 주어진 데이터를 가지고 AccessToken 발급
     *
     * @param data 토큰에 담을 데이터
     * @return AccessToken 정보를 담은 TokenDto
     */
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);

        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    /**
     * 주어진 데이터를 가지고 RefreshToken 발급
     *
     * @param data 토큰에 담을 데이터
     * @return RefreshToken 정보를 담은 TokenDto
     */
    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    /**
     * 주어진 토큰을 검증하고, 검증 결과를 Map으로 반환
     * 유효하지 않은 토큰, 만료된 토큰, 그 외의 에러 발생 시 ApiException 예외 처리
     *
     * @param token 검증할 JWT 토큰
     * @return 검증 결과를 담은 Map
     * @throws ApiException 토큰 검증에 실패한 경우 발생하는 예외
     */
    @Override
    public Map<String, Object> validateTokenWithThrow(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try {
            Jws<Claims> result = parser.parseClaimsJws(token);
            return new HashMap<>(result.getBody());
        } catch (SignatureException e) {
            // 유효하지 않은 토큰
            throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰
            throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
        } catch (Exception e) {
            // 그 외 에러
            throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
        }
    }
}
