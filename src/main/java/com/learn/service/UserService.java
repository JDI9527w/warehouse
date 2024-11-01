package com.learn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.AssignRoleDto;
import com.learn.entity.Auth;
import com.learn.entity.Role;
import com.learn.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    User getUserByCode(String userCode);
    List<User> listUsers(User user);

    List<Auth> listUserAuthById(int userId);

    boolean updateById(User user, QueryWrapper<User> queryWrapper);

    List<Role> listUserRole(Integer userId);

    boolean assignRoleByUserId(AssignRoleDto assignRoleDto);
}
