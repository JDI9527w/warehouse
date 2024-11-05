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
}
