package com.learn.DTO;

/*
import com.learn.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SysUser implements UserDetails {
    private User user;
    private List<? extends GrantedAuthority> authList;

    public SysUser(User user) {
        this.user = user;
    }

    public SysUser() {
        user = null;
    }

    public void setAuthList(List<? extends GrantedAuthority> authList) {
        this.authList = authList;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authList;
    }

    @Override
    public String getPassword() {
        String password = user.getUserPwd();
        user.setUserPwd("");
        return password;
    }

    @Override
    public String getUsername() {
        return user.getUserCode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
*/
