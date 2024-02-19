package com.ecommerce.membership.domain.user.business;

import com.ecommerce.db.user.UserEntity;
import com.ecommerce.membership.common.annotation.Business;
import com.ecommerce.membership.domain.token.business.TokenBusiness;
import com.ecommerce.membership.domain.token.controller.model.TokenResponse;
import com.ecommerce.membership.domain.user.controller.model.UserLoginRequest;
import com.ecommerce.membership.domain.user.controller.model.UserRegisterRequest;
import com.ecommerce.membership.domain.user.controller.model.UserResponse;
import com.ecommerce.membership.domain.user.converter.UserConverter;
import com.ecommerce.membership.domain.user.model.User;
import com.ecommerce.membership.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    /**
     * 회원가입 로직
     * 1. request -> entity 변환
     * 2. entity -> db 저장(save)
     * 3. db에 저장한 entity -> response 변환 -> return response
     *
     * @param request 회원가입 요청 정보
     * @return 회원가입 완료된 사용자 정보
     */
    public UserResponse register(UserRegisterRequest request) {

        // 1. request -> entity 변환
        UserEntity entity = userConverter.toEntity(request);

        // 2. entity -> db 저장(save)
        UserEntity newEntity = userService.register(entity);

        // 3. db에 저장한 entity -> response 변환 -> return response
        return userConverter.toResponse(newEntity);
    }

    /**
     * 로그인 로직
     * 1. email, password로 사용자(user) 검증
     * 2. user entity 로그인 확인
     * 3. token 생성 -> response 변환 -> return response
     *
     * @param request 로그인 요청 정보 (email, password)
     * @return 로그인 완료된 사용자의 토큰 정보
     */
    public TokenResponse login(UserLoginRequest request) {
        // 1. email, password로 사용자(user) 검증
        UserEntity userEntity = userService.login(request.getEmail(), request.getPassword()); // user가 없으면 throw

        // 2. user entity 로그인 확인
        // 3. token 생성 -> response 변환 -> return response
        return tokenBusiness.issueToken(userEntity);
    }

    /**
     * 현재 로그인된 사용자 정보를 조회하는 메소드
     *
     * @param user 현재 로그인된 사용자
     * @return 현재 로그인된 사용자 정보
     */
    public UserResponse me(User user) {
        UserEntity userEntity = userService.me(user);
        return userConverter.toResponse(userEntity);
    }
}
