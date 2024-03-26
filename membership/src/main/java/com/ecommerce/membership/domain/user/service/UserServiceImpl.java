package com.ecommerce.membership.domain.user.service;

import com.ecommerce.membership.axon.command.CreateUserCommand;
import com.ecommerce.membership.axon.command.DeleteUserCommand;
import com.ecommerce.membership.axon.command.LoginUserCommand;
import com.ecommerce.membership.axon.command.UpdateUserCommand;
import com.ecommerce.membership.common.error.UserErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.membership.domain.user.controller.model.UserCreateRequest;
import com.ecommerce.membership.domain.user.controller.model.UserDeleteRequest;
import com.ecommerce.membership.domain.user.controller.model.UserUpdateRequest;
import com.ecommerce.common.model.user.User;
import com.ecommerce.membership.entity.UserEntity;
import com.ecommerce.common.model.enums.UserStatus;
import com.ecommerce.membership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final CommandGateway commandGateway;
    private final UserRepository userRepository;
    private final Configuration configuration;

    /**
     * 사용자 생성
     * @param request 사용자 생성 요청 객체
     * @return 생성된 사용자의 정보
     * @throws ApiException 사용자 생성 과정에서 오류가 발생한 경우
     */
    @Override
    public String createUser(UserCreateRequest request) {
        String userId = UUID.randomUUID().toString();
        LocalDateTime registeredAt = LocalDateTime.now();

        CreateUserCommand command = new CreateUserCommand(
                userId,
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getAddress(),
                request.getThumbnailUrl(),
                request.getPhoneNumber(),
                request.getIsDoubleChecked(),
                registeredAt
        );

        try {
            commandGateway.send(command).get();
            log.info("사용자 생성 성공 : " + userId);
            return "사용자 생성 성공 : " + userId;
        } catch (InterruptedException ex) {
            String errorMessage = "사용자 생성 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(UserErrorCode.USER_CREATE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "사용자 생성 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(UserErrorCode.USER_CREATE_FAIL, errorMessage, ex);
        }
    }

    /**
     * 사용자 정보 업데이트
     * @param request 사용자 업데이트 요청 객체
     * @return 업데이트된 사용자의 정보
     * @throws ApiException 사용자 업데이트 과정에서 오류가 발생한 경우
     */
    @Override
    public String updateUser(UserUpdateRequest request) {
        String userId = request.getUserId();
        LocalDateTime updatedAt = LocalDateTime.now();

        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getAddress(),
                request.getThumbnailUrl(),
                request.getPhoneNumber(),
                request.getIsDoubleChecked(),
                updatedAt
        );

        try {
            commandGateway.send(command).get();
            log.info("사용자 정보 수정 성공 : " + userId);
            return "사용자 정보 수정 성공 : " + userId;
        } catch (InterruptedException ex) {
            String errorMessage = "사용자 정보 수정 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(UserErrorCode.USER_UPDATE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "사용자 정보 수정 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(UserErrorCode.USER_UPDATE_FAIL, errorMessage, ex);
        }
    }

    /**
     * 사용자 삭제
     * @param request 사용자 삭제 요청 객체
     * @return 삭제된 사용자의 정보
     * @throws ApiException 사용자 삭제 과정에서 오류가 발생한 경우
     */
    @Override
    public String deleteUser(UserDeleteRequest request) {
        String userId = request.getUserId();
        LocalDateTime unregisteredAt = LocalDateTime.now();

        DeleteUserCommand command = new DeleteUserCommand(
                userId,
                unregisteredAt
        );

        try {
            commandGateway.send(command).get();
            log.info("사용자 정보 삭제 성공 : " + userId);
            return "사용자 정보 삭제 성공 : " + userId;
        } catch (InterruptedException ex) {
            String errorMessage = "사용자 정보 삭제 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(UserErrorCode.USER_DELETE_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "사용자 정보 삭제 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(UserErrorCode.USER_DELETE_FAIL, errorMessage, ex);
        }
    }

    /**
     * 로그인
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @return 로그인한 사용자 엔티티
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserEntity login(String email, String password) {
        UserEntity entity = getUserWithThrow(email, password);

        String userId = entity.getUserId();
        LocalDateTime lastLoginAt = LocalDateTime.now();

        LoginUserCommand command = new LoginUserCommand(
                userId,
                lastLoginAt
        );

        try {
            commandGateway.send(command).get();
            log.info("사용자 로그인 성공 : " + userId);
            return entity;
        } catch (InterruptedException ex) {
            String errorMessage = "사용자 로그인 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            Thread.currentThread().interrupt();
            throw new ApiException(UserErrorCode.USER_LOGIN_FAIL, errorMessage, ex);
        } catch (ExecutionException ex) {
            String errorMessage = "사용자 로그인 중 오류 발생 (" + ex.getCause().getMessage() + ")";
            log.error(errorMessage);
            throw new ApiException(UserErrorCode.USER_LOGIN_FAIL, errorMessage, ex);
        }
    }

    /**
     * 이메일과 비밀번호로 사용자 엔티티 조회
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @return 찾아온 사용자 엔티티
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserEntity getUserWithThrow(String email, String password) {
        Optional<UserEntity> userEntity = userRepository.findFirstByEmailAndPasswordAndStatusOrderByUserIdDesc(
                email,
                password,
                UserStatus.REGISTERED
        );
        return userEntity.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자의 이메일을 사용해 사용자의 중복 여부 확인
     *
     * @param email 확인할 사용자의 이메일
     * @return 사용자가 존재하는지 여부를 나타내는 Boolean 값
     */
    @Override
    public Boolean doubleCheckEmail(String email) {
        Optional<UserEntity> entity = userRepository.findFirstByEmailAndStatusOrderByUserIdDesc(
                email,
                UserStatus.REGISTERED
        );

        return entity.isPresent();
    }

    /**
     * 현재 로그인된 사용자 정보를 조회하는 메소드
     *
     * @param user 현재 로그인된 사용자
     * @return 현재 로그인된 사용자 정보
     * @throws ApiException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserEntity me(User user) {
        Optional<UserEntity> userEntity = userRepository.findFirstByUserIdAndStatusOrderByUserIdDesc(
                user.getUserId(),
                user.getStatus());
        return userEntity.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 모든 사용자의 이벤트 처리 재설정
     */
    @Override
    public void resetUser() {
        configuration.eventProcessingConfiguration()
                .eventProcessorByProcessingGroup("users", TrackingEventProcessor.class)
                .ifPresent(trackingEventProcessor -> {
                    trackingEventProcessor.shutDown();
                    trackingEventProcessor.resetTokens();
                    trackingEventProcessor.start();
                });
    }
}
