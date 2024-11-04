package com.learn.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.AssignAuthDTO;
import com.learn.entity.Auth;
import com.learn.entity.Role;
import com.learn.entity.RoleAuth;
import com.learn.mapper.AuthMapper;
import com.learn.service.AuthService;
import com.learn.service.RoleAuthService;
import com.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "com.learn.service.impl.AuthServiceImpl")
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleAuthService roleAuthService;

    @Override
    public List<Auth> listUserAuthById(Integer userId) {
        String jsonString = redisTemplate.opsForValue().get("authTree:" + userId);
        List<Auth> auths;
        if (StringUtils.isEmpty(jsonString)) {
            auths = baseMapper.listUserAuthByUserId(userId);
            auths = treeAuth(auths);
            redisTemplate.opsForValue().set("authTree:" + userId, JSON.toJSONString(auths), 60 * 60 * 8, TimeUnit.SECONDS);
            return auths;
        }
        auths = JSONArray.parseArray(jsonString, Auth.class);
        return auths;
    }

    @Cacheable(key = "'all:authTree'")
    @Override
    public List<Auth> listAuthTree() {
        QueryWrapper<Auth> wq = new QueryWrapper<>();
        wq.ne("auth_type",3);
        wq.eq("auth_state", 1);
        List<Auth> authList = baseMapper.selectList(wq);
        return treeAuth(authList);
    }

    @Override
    public List<Integer> listUserAuthIdByUserId(Integer userId) {
        return baseMapper.listUserAuthIdByUserId(userId);
    }

    /**
     * 分配权限
     * @param assignAuthDTO
     * @return
     */
    @Override
    public boolean assignAuth(AssignAuthDTO assignAuthDTO) {
        // 根本是修改角色的权限. 如果多角色? 相应角色下列表权限全删,重新添加最方便.
        Integer userId = assignAuthDTO.getUserId();
        List<Integer> authIds = assignAuthDTO.getAuthIds();
        boolean flag = roleAuthService.delByUserId(userId);
        // 如果是空 删完就结束
        if (CollectionUtils.isEmpty(authIds)){
            return flag;
        }
        // 不是空,遍历,给拥有的每个角色添加相应权限.
        List<Integer> userRoleIdList = userService.listUserRole(userId).stream().map(Role::getRoleId).collect(Collectors.toList());
        List<RoleAuth> roleAuths = new ArrayList<>();
        for (Integer roleId : userRoleIdList) {
            for (Integer authId:authIds){
                roleAuths.add(new RoleAuth(roleId,authId));
            }
        }
        return roleAuthService.saveBatch(roleAuths);
    }

    /**
     * 树化权限
     * @param auths
     * @return
     */
    public List<Auth> treeAuth(List<Auth> auths) {
        List<Integer> parentIdList = new ArrayList<>();
        for (Auth auth : auths) {
            int parentId = auth.getParentId();
            if (parentId != 0) {
                if (parentIdList.contains(parentId)) {
                    continue;
                }
                parentIdList.add(parentId);
                List<Auth> father = auths.stream().filter(a -> a.getAuthId() == parentId).collect(Collectors.toList());
                if (father.isEmpty()) {
                    continue;
                }
                List<Auth> children = auths.stream().filter(a -> a.getParentId() == parentId).collect(Collectors.toList());
                Auth fatherAuth = father.get(0);
                fatherAuth.setChildAuth(children);
            }
        }
        auths = auths.stream().filter(auth -> auth.getParentId() == 0).collect(Collectors.toList());
        return auths;
    }
}
