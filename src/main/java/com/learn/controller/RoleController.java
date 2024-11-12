package com.learn.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.AssignRoleAuthDTO;
import com.learn.DTO.Result;
import com.learn.entity.Role;
import com.learn.service.RoleAuthService;
import com.learn.service.RoleService;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RoleAuthService roleAuthService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/role-list")
    public Result listRole() {
        List<Role> roleList;
        QueryWrapper<Role> queryWrapper = new QueryWrapper();
        String stringRoleList = redisTemplate.opsForValue().get("role:list");
        if (StringUtils.isEmpty(stringRoleList)) {
            queryWrapper.eq("role_state", WarehouseConstants.STATE_ENABLED);
            roleList = roleService.listRole(queryWrapper);
            String jsonRoleList = JSON.toJSONString(roleList);
            redisTemplate.opsForValue().set("role:list", jsonRoleList);
            return Result.ok(roleList);
        }
        roleList = JSON.parseArray(stringRoleList, Role.class);
        return Result.ok(roleList);
    }

    /**
     * 角色分页查询
     *
     * @param role
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/role-page-list")
    public Result rolePage(Role role,
                           @RequestParam Integer pageSize,
                           @RequestParam Integer pageNum) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (role != null) {
            if (StringUtils.isNotEmpty(role.getRoleName())) {
                queryWrapper.eq("role_name", role.getRoleName());
            }
            if (StringUtils.isNotEmpty(role.getRoleCode())) {
                queryWrapper.eq("role_code", role.getRoleCode());
            }
            if (role.getRoleState() != null) {
                queryWrapper.eq("role_state", role.getRoleState());
            }
        }
        Page<Role> page = new Page<>(pageNum, pageSize);
        Page<Role> pageRole = roleService.page(page, queryWrapper);
        return Result.ok(pageRole);
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @PostMapping("/role-add")
    public Result addRole(@RequestBody Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(role);
        Role one = roleService.getOne(queryWrapper);
        if (one == null) {
            role.setRoleState(WarehouseConstants.STATE_DISABLED);
            boolean save = roleService.save(role);
            if (save) {
                redisTemplate.delete("role:list");
                return Result.ok("操作成功");
            }
            return Result.err(Result.CODE_ERR_SYS, "操作失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "操作失败,该角色已存在");
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @PutMapping("/role-update")
    public Result updateRole(@RequestBody Role role) {
        boolean flag = roleService.updateById(role);
        if (flag) {
            redisTemplate.delete("role:list");
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("/role-delete/{roleId}")
    public Result deleteRoleById(@PathVariable Integer roleId) {
        boolean flag = roleService.removeRoleById(roleId);
        if (flag) {
            redisTemplate.delete("role:list");
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败,此角色已分配,请撤销分配后再试");
    }

    /**
     * 修改角色状态,如果角色禁用,则赋予了此角色的用户无法使用该角色的权限.
     *
     * @param role
     * @return
     */
    @PutMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role) {
        boolean flag = roleService.updateById(role);
        if (flag) {
            redisTemplate.delete("role:list");
            Set<String> keys = redisTemplate.keys("authTree*");
            if (CollectionUtils.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    /**
     * 获取角色对应的权限id
     *
     * @param roleId
     * @return
     */
    @GetMapping("/role-auth")
    public Result roleAuthByRoleId(Integer roleId) {
        List<Integer> list = roleAuthService.listAuthIdByRoleId(roleId);
        return Result.ok(list);
    }

    @PutMapping("/auth-grant")
    public Result authGrant(@RequestBody AssignRoleAuthDTO araDTO) {
        boolean flag = roleService.authGrantByRoleId(araDTO);
        if (flag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @GetMapping("/exportTable")
    public Result exportRoleList() {
        return Result.ok(roleService.list());
    }
}
