package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.LoginUser;
import com.learn.DTO.Result;
import com.learn.DTO.SysUser;
import com.learn.entity.Role;
import com.learn.entity.User;
import com.learn.mapper.UserMapper;
import com.learn.service.UserService;
import com.learn.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public User getUserByCode(String userCode) {
        return userMapper.getUserByCode(userCode);
    }

    @Override
    public List<User> listUsers(User user) {
        return userMapper.listUsers(user);
    }

    @Override
    public boolean updateById(User user, QueryWrapper<User> queryWrapper) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name", user.getUserName());
        User exist = baseMapper.selectOne(qw);
        if (exist != null) {
            return false;
        }
        return baseMapper.update(user, queryWrapper) > 0;
    }

    @Override
    public List<Role> listUserRole(Integer userId) {
        return baseMapper.listUserRole(userId);
    }

    @Override
    public Result listUserExport() {
        return Result.ok(baseMapper.listUserExport());
    }

    @Override
    public Result login(LoginUser loginUser) {
        String verificationCode = loginUser.getVerificationCode();
        if (StringUtils.isNotEmpty(verificationCode) && !redisTemplate.hasKey(verificationCode)){
            return Result.err(403,"验证码错误，请重新输入");
        }
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(loginUser.getUserCode(),loginUser.getUserPwd());
        Authentication authenticate = authenticationManager.authenticate(upat);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        SysUser sysUser = (SysUser)authenticate.getPrincipal();
        String token = tokenUtils.loginSign(sysUser, sysUser.getPassword());
        return Result.ok("登录成功",token);
    }
}
