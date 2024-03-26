package com.ecommerce.membership.axon.aggregate;

import com.ecommerce.common.axon.event.membership.*;
import com.ecommerce.membership.axon.command.CreateUserCommand;
import com.ecommerce.membership.axon.command.DeleteUserCommand;
import com.ecommerce.membership.axon.command.LoginUserCommand;
import com.ecommerce.membership.axon.command.UpdateUserCommand;
import com.ecommerce.membership.common.error.UserErrorCode;
import com.ecommerce.membership.common.exception.ApiException;
import com.ecommerce.common.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Aggregate(snapshotTriggerDefinition = "userSnapshotTriggerDefinition", cache = "userCache")
@EqualsAndHashCode
public class UserAggregate {
    @AggregateIdentifier
    private String userId;
    private String name;
    private String email;
    private String password;
    private UserStatus status;
    private String address;
    private String thumbnailUrl;
    private String phoneNumber;
    private Boolean isDoubleChecked;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime updatedAt;

    @CommandHandler
    public UserAggregate(CreateUserCommand command) {
        log.info("handling {}", command);

        if (!Objects.equals(Boolean.TRUE, command.getIsDoubleChecked())) {
            throw new ApiException(UserErrorCode.USER_NAME_DOUBLED);
        }

        apply(new UserCreatedEvent(
                command.getUserId(),
                command.getName(),
                command.getEmail(),
                command.getPassword(),
                command.getAddress(),
                command.getThumbnailUrl(),
                command.getPhoneNumber(),
                Boolean.TRUE,
                command.getRegisteredAt()
                )
        );
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        log.info("handling {}", event);
        this.userId = event.getUserId();
        this.name = event.getName();
        this.email = event.getEmail();
        this.password = event.getPassword();
        this.status = UserStatus.REGISTERED;
        this.address = event.getAddress();
        this.thumbnailUrl = event.getThumbnailUrl();
        this.phoneNumber = event.getPhoneNumber();
        this.isDoubleChecked = event.getIsDoubleChecked();
        this.registeredAt = event.getRegisteredAt();
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        log.info("handling {}", command);
        if (!Objects.equals(this.userId, command.getUserId())) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }

        if (!Objects.equals(Boolean.TRUE, command.getIsDoubleChecked())) {
            throw new ApiException(UserErrorCode.USER_NAME_DOUBLED);
        }

        if (!Objects.equals(this.status, UserStatus.UNREGISTERED)) {
            apply(new UserUpdatedEvent(
                    command.getUserId(),
                    command.getName(),
                    command.getEmail(),
                    command.getPassword(),
                    command.getAddress(),
                    command.getThumbnailUrl(),
                    command.getPhoneNumber(),
                    command.getIsDoubleChecked(),
                    command.getUpdatedAt()
                    )
            );
        } else {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND, "해당 사용자는 이미 삭제되었습니다.");
        }
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event) {
        log.info("handling {}", event);
        this.name = event.getName();
        this.email = event.getEmail();
        this.password = event.getPassword();
        this.address = event.getAddress();
        this.thumbnailUrl = event.getThumbnailUrl();
        this.phoneNumber = event.getPhoneNumber();
        this.isDoubleChecked = event.getIsDoubleChecked();
        this.updatedAt = event.getUpdatedAt();
    }

    @CommandHandler
    public void handle(DeleteUserCommand command) {
        log.info("handling {}", command);

        if (!Objects.equals(this.userId, command.getUserId())) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }

        if (!Objects.equals(this.status, UserStatus.UNREGISTERED)) {
            apply(new UserDeletedEvent(
                    command.getUserId(),
                    command.getUnregisteredAt()
                    )
            );
        } else {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND, "해당 사용자는 이미 삭제되었습니다.");
        }
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent event) {
        log.info("handling {}", event);
        this.status = UserStatus.UNREGISTERED;
        this.unregisteredAt = event.getUnregisteredAt();
    }

    @CommandHandler
    public void handle(LoginUserCommand command) {
        log.info("handling {}", command);

        if (!Objects.equals(this.userId, command.getUserId())) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }

        if (!Objects.equals(this.status, UserStatus.UNREGISTERED)) {
            apply(new UserLoginEvent(
                    command.getUserId(),
                    command.getLastLoginAt()
                    )
            );
        } else {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND, "해당 사용자는 이미 삭제되었습니다.");
        }
    }

    @EventSourcingHandler
    public void on(UserLoginEvent event) {
        log.info("handling {}", event);
        this.unregisteredAt = event.getLastLoginAt();
    }
}
