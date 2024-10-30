package com.learn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.CurrentUser;
import com.learn.DTO.Result;
import com.learn.entity.Auth;
import com.learn.entity.User;
import com.learn.service.UserService;
import com.learn.util.TokenUtils;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping("/user/auth-list")
    public Result listUserAuth(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        List<Auth> auths = userService.listUserAuthById(currentUser.getUserId());
        return Result.ok(auths);
    }

    @GetMapping("/user/user-list")
    public Result listUser(@RequestBody User user,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("pageNum") Integer pageNum) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if (user.getUserCode() != null && user.getUserCode() != "") {
            queryWrapper.eq("user_code", user.getUserCode());
        }
        if (user.getUserType() != null) {
            queryWrapper.eq("user_type", user.getUserType());
        }
        if (user.getUserState() != null) {
            queryWrapper.eq("user_state", user.getUserState());
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> pageList = userService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

}
