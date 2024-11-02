package com.learn.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.DTO.Result;
import com.learn.entity.Role;
import com.learn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RoleService roleService;

    @GetMapping("/role/role-list")
    public Result listRole(){
        List<Role> roleList;
        QueryWrapper<Role> queryWrapper = new QueryWrapper();
        String stringRoleList = redisTemplate.opsForValue().get("role:list");
        if (StringUtils.isEmpty(stringRoleList)){
            roleList = roleService.listRole(queryWrapper);
            String jsonRoleList = JSON.toJSONString(roleList);
            redisTemplate.opsForValue().set("role:list",jsonRoleList);
            // TODO 权限表更新时,及时删除redis.
            return Result.ok(roleList);
        }
        roleList = JSON.parseArray(stringRoleList, Role.class);
        return Result.ok(roleList);
    }

}
