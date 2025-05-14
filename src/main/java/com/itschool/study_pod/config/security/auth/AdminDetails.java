package com.itschool.study_pod.config.security.auth;

import com.itschool.study_pod.enumclass.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class AdminDetails implements UserDetails {

    private final String adminId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(AccountRole.ROLE_MODERATOR.toString()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return adminId;
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료되지 않았는지를 리턴, true면 만료되지 않음
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겨있지 않았는지를 리턴, true면 잠겨있지 않음
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //계정 비밀번호가 만료되지 않았는지를 리턴
        return true;
    }

    @Override
    public boolean isEnabled() { // 사용 가능한 게정인지를 리턴
        return true;
    }

}