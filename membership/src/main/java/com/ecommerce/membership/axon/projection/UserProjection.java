package com.ecommerce.membership.axon.projection;

import com.ecommerce.common.axon.event.membership.*;
import com.ecommerce.membership.domain.user.converter.UserConverter;
import com.ecommerce.membership.entity.UserEntity;
import com.ecommerce.common.model.enums.UserStatus;
import com.ecommerce.membership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@ProcessingGroup("users")
@Slf4j
@Component
@RequiredArgsConstructor
public class UserProjection {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @ResetHandler
    private void resetUserModel() {
        log.info("reset Store Model triggered");
        userRepository.deleteAll();
    }

    @AllowReplay
    @EventHandler
    public void on(UserCreatedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 사용자 정보 생성 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        UserEntity entity = userConverter.toEntity(event);
        entity.setStatus(UserStatus.REGISTERED);
        entity.setRegisteredAt(event.getRegisteredAt());
        userRepository.save(entity);
    }

    @AllowReplay
    @EventHandler
    public void on(UserUpdatedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 사용자 정보 수정 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        userRepository.updateUserByEvent(
                event.getUserId(),
                event.getName(),
                event.getEmail(),
                event.getPassword(),
                event.getAddress(),
                event.getThumbnailUrl(),
                event.getPhoneNumber(),
                event.getIsDoubleChecked(),
                event.getUpdatedAt()
        );
    }

    @AllowReplay
    @EventHandler
    public void on(UserDeletedEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 사용자 정보 삭제 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        userRepository.deleteUserByEvent(
                event.getUserId(),
                UserStatus.UNREGISTERED,
                event.getUnregisteredAt()
        );
    }

    @AllowReplay
    @EventHandler
    public void on(UserLoginEvent event, @SequenceNumber Long sequenceNumber, @Timestamp Instant instant) {
        log.info("DB에 사용자 마지막 로그인 시간 수정 성공 : {}, SequenceNumber : {}, Timestamp : {}", event, sequenceNumber, instant.toString());
        userRepository.updateUserLastLoginAtByEvent(
                event.getUserId(),
                event.getLastLoginAt()
        );
    }
}
