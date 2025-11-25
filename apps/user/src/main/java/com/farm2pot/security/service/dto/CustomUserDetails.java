package com.farm2pot.security.service.dto;

import com.farm2pot.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * packageName    : com.farm2pot.auth.dto
 * author         : TAEJIN
 * date           : 2025-10-04
 * description    : userName을 쓰지않으려고 custom함.. username 대신에 loginId
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final String loginId;
    private final String password;
    private final int status;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }

    public String getLoginId() { return getUsername();}
}
