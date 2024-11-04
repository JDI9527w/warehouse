package com.learn.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.AssignRoleDto;
import com.learn.entity.Role;
import com.learn.entity.UserRole;
import com.learn.mapper.RoleMapper;
import com.learn.mapper.UserRoleMapper;
import com.learn.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 为用户分配角色
     * @param assignRoleDto
     * @return
     */
    @Override
    @Transactional
    public boolean assignRoleByUserId(AssignRoleDto assignRoleDto) {
        Integer userId = assignRoleDto.getUserId();
        List<String> roleCheckList = assignRoleDto.getRoleCheckList();
        List<UserRole> userRoleList = this.getUserRoleList(roleCheckList, userId);
        if (CollectionUtils.isEmpty(userRoleList)) {
            return true;
        }
        baseMapper.delUserRoleByUserID(userId);
        int num = baseMapper.insertUserRoleBatch(userRoleList);
        if (num > 0){
            return true;
        }
        return false;
    }

    /**
     * 查找绑定了指定角色id的数据
     * @param roleId
     * @return
     */
    @Override
    public List<UserRole> listUserRoleByRoleId(Integer roleId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 通过RoleName和userId,返回userRole列表
     * @param roleCheckList
     * @param userId
     * @return
     */
    List<UserRole> getUserRoleList(List<String> roleCheckList, Integer userId) {
        String roleListJson = redisTemplate.opsForValue().get("role:list");
        List<Role> roleList;
        if (StringUtils.isEmpty(roleListJson)){
            roleList = roleMapper.selectList(new QueryWrapper<>());
        } else {
            roleList = JSON.parseArray(roleListJson, Role.class);
        }
        List<UserRole> userRoleList = new ArrayList<>();
        for (String roleName : roleCheckList) {
            assert roleList != null;
            Role role = roleList.stream().filter(r -> r.getRoleName().equals(roleName)).collect(Collectors.toList()).get(0);
            userRoleList.add(new UserRole(role.getRoleId(),userId));
        }
        return userRoleList;
    }
}
