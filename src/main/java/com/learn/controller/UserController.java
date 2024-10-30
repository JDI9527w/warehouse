package com.learn.controller;

import com.alibaba.fastjson2.JSON;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        String jsonString = redisTemplate.opsForValue().get("authTree:" + currentUser.getUserId());
        if (StringUtils.isEmpty(jsonString)) {
            List<Auth> auths = userService.listUserAuthById(currentUser.getUserId());
            jsonString = JSON.toJSONString(auths);
            redisTemplate.opsForValue().set("authTree:" + currentUser.getUserId(), jsonString, 60 * 60 * 8, TimeUnit.SECONDS);
        }
        return Result.ok(jsonString);
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
