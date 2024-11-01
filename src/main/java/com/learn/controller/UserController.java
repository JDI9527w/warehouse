package com.learn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.AssignRoleDto;
import com.learn.DTO.CurrentUser;
import com.learn.DTO.Result;
import com.learn.entity.Auth;
import com.learn.entity.Role;
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
        if (user != null) {
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
        Page<User> pageList = userService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    @PostMapping("user/addUser")
    public Result addUser(@RequestBody User user) {

        boolean flag = userService.save(user);
        if (flag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "操作失败");
    }

    @PutMapping("/user/updateUser")
    public Result updateUser(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        boolean updateFlag = userService.updateById(user, queryWrapper);
        if (updateFlag) {
            return Result.ok("修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败,昵称重复");
    }

    @DeleteMapping("/user/deleteUser/{userId}")
    public Result deleteUserById(@PathVariable Integer userId) {
        boolean removeFlag = userService.remove(new QueryWrapper<User>().eq("user_id", userId));
        if (removeFlag) {
            return Result.ok("删除成功.");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败");
    }

    @PutMapping("/user/updateState")
    public Result updateState(@RequestBody User user) {
        boolean updateFlag = userService.updateById(user);
        if (updateFlag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @GetMapping("/user/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId) {
        List<Role> roles = userService.listUserRole(userId);
        return Result.ok(roles);
    }

    @PutMapping("/user/assignRole")
    public Result assignRoleByUserId(@RequestBody AssignRoleDto assignRoleDto) {
        boolean b = userService.assignRoleByUserId(assignRoleDto);
        // TODO if can't do change web ,cache roleList to redis.
        return null;
    }
}
