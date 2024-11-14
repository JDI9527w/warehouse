package com.learn.DTO;

import com.learn.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SysUser implements UserDetails {
    private final User user;
    private List<SimpleGrantedAuthority> authList;

    public SysUser(User user) {
        this.user = user;
    }

    public SysUser() {
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setAuthList(List<SimpleGrantedAuthority> authList) {
        this.authList = authList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authList;
    }

    @Override
    public String getPassword() {
        return user.getUserPwd();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }
    // 指示用户的帐户是否已过期。无法对过期的帐户进行身份验证。
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 指示用户是被锁定还是解锁。无法对锁定的用户进行身份验证。
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 指示用户的帐户是否已过期。无法对过期的帐户进行身份验证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 指示用户是启用还是禁用。无法对已禁用的用户进行身份验证。
    @Override
    public boolean isEnabled() {
        return true;
    }
}
