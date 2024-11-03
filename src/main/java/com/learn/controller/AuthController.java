package com.learn.controller;

import com.learn.DTO.Result;
import com.learn.entity.Auth;
import com.learn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/auth/auth-tree")
    public Result authTree(){
        List<Auth> treeAuthList = authService.listAuthTree();
        return Result.ok(treeAuthList);
    }
    // TODO 修改权限为修改用户拥有角色的权限，如果有多个角色，则每个角色都添加/删除响应的权限。
}
