package com.ecommerce.membership.domain.user.business;

import com.ecommerce.membership.common.annotation.Business;
import com.ecommerce.membership.common.error.UserErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.token.business.TokenBusiness;
import com.ecommerce.membership.domain.token.controller.model.TokenResponse;
import com.ecommerce.membership.domain.user.controller.model.*;
import com.ecommerce.membership.domain.user.converter.UserConverter;
import com.ecommerce.common.model.user.User;
import com.ecommerce.membership.domain.user.service.UserService;
import com.ecommerce.membership.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    /**
     * 사용자 생성
     * @param request 사용자 생성 요청 객체
     * @return 생성된 사용자에 대한 정보
     */
    public String createUser(UserCreateRequest request) {
        return userService.createUser(request);
    }

    /**
     * 사용자 정보 업데이트
     * @param request 사용자 업데이트 요청 객체
     * @param user 업데이트할 사용자 객체
     * @throws ApiException 사용자 세션을 찾을 수 없을 때 발생하는 예외
     * @return 업데이트된 사용자에 대한 정보
     */
    public String updateUser(UserUpdateRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }
        return userService.updateUser(request);
    }

    /**
     * 사용자 삭제
     * @param request 사용자 삭제 요청 객체
     * @param user 삭제할 사용자 객체
     * @throws ApiException 사용자 세션을 찾을 수 없을 때 발생하는 예외
     * @return 삭제된 사용자에 대한 정보
     */
    public String deleteUser(UserDeleteRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }
        return userService.deleteUser(request);
    }

    /**
     * 로그인
     * 1. email, password로 사용자(user) 검증
     * 2. user entity 로그인 확인
     * 3. token 생성 -> response 변환 -> return response
     *
     * @param request 로그인 요청 정보 (email, password)
     * @return 로그인 완료된 사용자의 토큰 정보
     */
    public TokenResponse login(UserLoginRequest request) {
        UserEntity userEntity = userService.login(request.getEmail(), request.getPassword());
        return tokenBusiness.issueToken(userEntity);
    }

    /**
     * 사용자 이메일 중복 확인
     *
     * @param email 사용자 이메일
     * @return 중복 여부를 나타내는 불리언 값
     */
    public Boolean doubleCheckEmail(String email) {
        return userService.doubleCheckEmail(email);
    }

    /**
     * 현재 로그인된 사용자 정보를 조회하는 메소드
     *
     * @param user 현재 로그인된 사용자
     * @return 현재 로그인된 사용자 정보
     */
    @Cacheable(cacheNames = "UserMe", key = "#user.userId")
    public UserResponse me(User user) {
        UserEntity userEntity = userService.me(user);
        return userConverter.toResponse(userEntity);
    }

    /**
     * 사용자 정보 재설정
     */
    public void resetUser() {
        userService.resetUser();
    }
}
