package com.itschool.study_pod.global.base.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AccountDetails implements UserDetails {

    private final Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(account.getRole().name()));
    }

    public Long getId() {
        return account.getId();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
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
        return account.getUpdatedAt().isAfter(expiredThreshold);
    }

    // 계정 활성화 여부 (가입 후 이메일 인증 등)
    @Override
    public boolean isEnabled() {
        return true; // 구현 시 구분 필드 필요
    }
}
