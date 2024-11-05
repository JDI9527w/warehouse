package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.AssignRoleAuthDTO;
import com.learn.entity.Role;
import com.learn.entity.RoleAuth;
import com.learn.entity.UserRole;
import com.learn.mapper.RoleMapper;
import com.learn.service.RoleAuthService;
import com.learn.service.RoleService;
import com.learn.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleAuthService roleAuthService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Role> listRole(QueryWrapper<Role> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean removeRoleById(Integer roleId) {
        List<UserRole> userRoleList = userRoleService.listUserRoleByRoleId(roleId);
        if (CollectionUtils.isEmpty(userRoleList)) {
            return baseMapper.deleteById(roleId) > 1;
        }
        return false;
    }

    @Override
    public boolean updateRoleState(Role role) {
        // TODO: 干嘛用的?
        return false;
    }

    @Override
    public boolean authGrantByRoleId(AssignRoleAuthDTO araDTO) {
        Integer roleId = araDTO.getRoleId();
        List<Integer> authIds = araDTO.getAuthIds();
        roleAuthService.remove(new QueryWrapper<RoleAuth>().eq("role_id", roleId));
        if (CollectionUtils.isEmpty(authIds)) {
            return true;
        }
        List<RoleAuth> roleAuthList = authIds.stream().map(id -> new RoleAuth(roleId, id)).collect(Collectors.toList());
        return roleAuthService.saveBatch(roleAuthList);
    }
}
