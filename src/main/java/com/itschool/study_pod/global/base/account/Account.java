package com.itschool.study_pod.global.base.account;

import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.enumclass.AccountRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@DiscriminatorColumn(name = "dtype") // optional
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accounts")
public abstract class Account extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected AccountRole role;

    @Column(nullable = false)
    protected String name;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true; // 구현 시 구분 필드 필요
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true; // 구현 시 구분 필드 필요
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        LocalDateTime expiredThreshold = LocalDateTime.now().minusDays(90);

        // 최종 수정일이 90일 이내인지 확인
        return getUpdatedAt().isAfter(expiredThreshold);
    }

    // 계정 활성화 여부 (가입 후 이메일 인증 등)
    @Override
    public boolean isEnabled() {
        return true; // 구현 시 구분 필드 필요
    }
}
