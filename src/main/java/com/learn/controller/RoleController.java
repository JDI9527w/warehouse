package com.learn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.DTO.Result;
import com.learn.entity.Role;
import com.learn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;
    @GetMapping("/role/role-list")
    public Result listRole(){
        QueryWrapper<Role> queryWrapper = new QueryWrapper();
        List<Role> roleList = roleService.listRole(queryWrapper);
        return Result.ok(roleList);
    }

}
