package com.learn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.DTO.Result;
import com.learn.entity.Auth;
import com.learn.service.AuthService;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/auth/auth-tree")
    public Result authTree() {
        List<Auth> treeAuthList = authService.listAuthTree();
        return Result.ok(treeAuthList);
    }

    @GetMapping("/auth/name-check")
    public Result checkAuthName(String authName) {
        QueryWrapper<Auth> qw = new QueryWrapper<>();
        qw.eq("auth_name", authName);
        Auth one = authService.getOne(qw);
        if (one != null) {
            return Result.ok("此名称已经存在.", "此名称已经存在");
        }
        return Result.ok();
    }

    @PostMapping("/auth/auth-add")
    public Result addAuth(@RequestBody Auth auth) {
        auth.setAuthState(WarehouseConstants.STATE_DISABLED);
        auth.setAuthOrder(0);
        boolean save = authService.save(auth);
        if (save) {
            redisTemplate.delete("com.learn.service.impl.AuthServiceImpl::all:authTree");
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "操作失败");
    }

    @GetMapping("/auth/url-check")
    public Result checkAuthUrl(String url) {
        QueryWrapper<Auth> qw = new QueryWrapper<>();
        qw.eq("auth_url", url);
        Auth one = authService.getOne(qw);
        if (one != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "此url已经存在.");
        }
        return Result.ok("ok", "此url已经存在");
    }

    @PutMapping("/auth/auth-enable/{authId}")
    public Result enableAuth(@PathVariable Integer authId) {
        Auth auth = authService.getById(authId);
        if (auth != null) {
            auth.setAuthState(WarehouseConstants.STATE_ENABLED);
            authService.updateById(auth);
            redisTemplate.delete("com.learn.service.impl.AuthServiceImpl::all:authTree");
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "没有与找到对应的权限");
    }

    @PutMapping("/auth/auth-disable/{authId}")
    public Result disableAuth(@PathVariable Integer authId) {
        Auth auth = authService.getById(authId);
        if (auth != null) {
            auth.setAuthState(WarehouseConstants.STATE_DISABLED);
            authService.updateById(auth);
            redisTemplate.delete("com.learn.service.impl.AuthServiceImpl::all:authTree");
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "没有与找到对应的权限");
    }

    @DeleteMapping("/auth/auth-delete/{authId}")
    public Result deleteAuthByAuthId(@PathVariable Integer authId) {
        boolean flag = authService.deleteAuthByAuthId(authId);
        if (flag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }
}
