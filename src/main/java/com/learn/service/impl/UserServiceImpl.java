package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.Result;
import com.learn.entity.Role;
import com.learn.entity.User;
import com.learn.mapper.UserMapper;
import com.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

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
        qw.eq("user_name",user.getUserName());
        User exist = baseMapper.selectOne(qw);
        if (exist != null){
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
}
