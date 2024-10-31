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
    public Result listUser(@RequestBody(required = false) User user,
                           @RequestParam Integer pageSize,
                           @RequestParam Integer pageNum) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if (user != null){
            if (user.getUserCode() != null && !user.getUserCode().equals("")) {
                queryWrapper.eq("user_code", user.getUserCode());
            }
            if (user.getUserType() != null) {
                queryWrapper.eq("user_type", user.getUserType());
            }
            if (user.getUserState() != null) {
                queryWrapper.eq("user_state", user.getUserState());
            }
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> pageList = userService.page(page);
        return Result.ok(pageList);
    }

    @PostMapping("user/addUser")
    public Result addUser(@RequestBody User user){
        boolean flag = userService.save(user);
        if (flag){
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "操作失败");
    }

}
