package com.learn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.AssignAuthDTO;
import com.learn.DTO.AssignRoleDto;
import com.learn.DTO.CurrentUser;
import com.learn.DTO.Result;
import com.learn.entity.Auth;
import com.learn.entity.Role;
import com.learn.entity.User;
import com.learn.service.AuthService;
import com.learn.service.UserRoleService;
import com.learn.service.UserService;
import com.learn.util.DigestUtil;
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
    private UserRoleService userRoleService;
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取用户权限
     *
     * @param token
     * @return
     */
    @GetMapping("/user/auth-list")
    public Result listUserAuth(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        List<Auth> auths = authService.listUserAuthById(currentUser.getUserId());
        return Result.ok(auths);
    }

    /**
     * 获取用户列表
     *
     * @param user
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/user/user-list")
    public Result listUser(User user,
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

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("user/addUser")
    public Result addUser(@RequestBody User user) {
        String parsePwd = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserState(WarehouseConstants.USER_STATE_NOT_PASS);
        user.setIsDelete(WarehouseConstants.LOGIC_NOT_DELETE_VALUE);
        user.setUserPwd(parsePwd);
        boolean flag = userService.save(user);
        if (flag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "操作失败");
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
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

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/user/deleteUser/{userId}")
    public Result deleteUserById(@PathVariable Integer userId) {
        boolean removeFlag = userService.remove(new QueryWrapper<User>().eq("user_id", userId));
        if (removeFlag) {
            return Result.ok("删除成功.");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败");
    }

    /**
     * 更改用户状态
     *
     * @param user
     * @return
     */
    @PutMapping("/user/updateState")
    public Result updateState(@RequestBody User user) {
        boolean updateFlag = userService.updateById(user);
        if (updateFlag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId) {
        List<Role> roles = userService.listUserRole(userId);
        return Result.ok(roles);
    }

    /**
     * 授权角色
     *
     * @param assignRoleDto
     * @return
     */
    @PutMapping("/user/assignRole")
    public Result assignRoleByUserId(@RequestBody AssignRoleDto assignRoleDto) {
        boolean flag = userRoleService.assignRoleByUserId(assignRoleDto);
        if (flag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @PutMapping("/user/updatePwd/{userId}")
    public Result resetPwdById(@PathVariable Integer userId) {
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.eq("user_id", userId);
        String parsePwd = DigestUtil.hmacSign("123456");
        uw.set("user_pwd", parsePwd);
        boolean update = userService.update(uw);
        if (update) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    /**
     * 获取用户权限id列表。
     * @param userId
     * @return
     */
    @GetMapping("/user/user-auth")
    public Result userAuth(Integer userId) {
        List<Integer> userAuthIdList = authService.listUserAuthIdByUserId(userId);
        return Result.ok(userAuthIdList);
    }

    @PutMapping("/user/auth-grant")
    public Result authGrant(@RequestBody AssignAuthDTO assignAuthDTO){
        boolean flag = authService.assignAuth(assignAuthDTO);
        if (flag) {
            return Result.ok("操作成功");
        }
        return  Result.err(Result.CODE_ERR_SYS,"操作失败");
    }
}
