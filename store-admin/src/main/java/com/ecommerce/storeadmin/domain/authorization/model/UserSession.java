package com.ecommerce.storeadmin.domain.authorization.model;

import com.ecommerce.storeadmin.entity.enums.StoreUserRole;
import com.ecommerce.storeadmin.entity.enums.StoreUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * UserDetails를 구현해 현재 사용자 세션 정보를 나타내는 클래스
 * Spring Security에서 사용자 인증에 필요한 정보 제공
 * 사용자의 권한은 Spring Security에서 요구하는 {@link GrantedAuthority} 형태로 제공
 * 사용자의 계정 상태는 {@link UserDetails}의 메서드를 통해 확인
 * 현재는 계정이 활성화된 상태일 때만 인증 허용
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession implements UserDetails {

    //user
    private Long userId;

    private String email;

    private String password;

    private StoreUserStatus status;

    private StoreUserRole role;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;

    //store
    private Long storeId;

    private String storeName;

    /**
     * 사용자의 권한을 Spring Security에서 요구하는 형태로 반환
     * @return 사용자의 권한을 담은 {@link SimpleGrantedAuthority}의 리스트
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(this.role.toString())
        );
    }

    /**
     * 사용자의 비밀번호 반환
     * @return 사용자의 비밀번호 (해싱된 형태)
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * 사용자의 이메일 주소 반환
     * @return 사용자의 이메일 주소
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * 사용자 계정이 만료되지 않았는지 확인
     * @return 사용자 계정이 활성화된 상태인 경우 true, 그렇지 않은 경우 false
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.status == StoreUserStatus.REGISTERED;
    }

    /**
     * 사용자 계정이 잠겨있지 않았는지 확인
     * @return 사용자 계정이 활성화된 상태인 경우 true, 그렇지 않은 경우 false
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.status == StoreUserStatus.REGISTERED;
    }

    /**
     * 사용자의 자격 증명이 만료되지 않았는지 확인
     * @return 사용자 계정이 활성화된 상태인 경우 true, 그렇지 않은 경우 false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == StoreUserStatus.REGISTERED;
    }

    /**
     * 사용자 계정이 활성화되어 있는지 확인
     * @return 사용자 계정이 활성화된 상태인 경우 true, 그렇지 않은 경우 false
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
