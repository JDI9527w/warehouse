package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.entity.Auth;
import com.learn.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    User getUserByCode(String userCode);
    List<User> listUsers(User user);

    List<Auth> listUserAuthById(int userId);
}
