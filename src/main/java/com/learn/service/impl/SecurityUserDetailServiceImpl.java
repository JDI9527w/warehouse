package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.DTO.SysUser;
import com.learn.entity.Auth;
import com.learn.entity.User;
import com.learn.exception.BusinessException;
import com.learn.service.AuthService;
import com.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_code", username);
        User user = userService.getOne(qw);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        SysUser sysUser = new SysUser(user);
        List<Auth> auths = authService.listUserAuthById(user.getUserId());
        List<String> collect = auths.stream().map(Auth::getAuthCode).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        List<SimpleGrantedAuthority> collect1 = collect.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        sysUser.setAuthList(collect1);
        return sysUser;
    }
}
