package com.learn.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.entity.Auth;
import com.learn.entity.Role;
import com.learn.entity.User;
import com.learn.mapper.UserMapper;
import com.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public User getUserByCode(String userCode) {
        return userMapper.getUserByCode(userCode);
    }

    @Override
    public List<User> listUsers(User user) {
        return userMapper.listUsers(user);
    }

    @Override
    public List<Auth> listUserAuthById(int userId) {
        String jsonString = redisTemplate.opsForValue().get("authTree:" + userId);
        List<Auth> auths;
        if (StringUtils.isEmpty(jsonString)) {
            auths = userMapper.listUserAuthById(userId);
            auths = treeAuth(auths);
            redisTemplate.opsForValue().set("authTree:" + userId, JSON.toJSONString(auths), 60 * 60 * 8, TimeUnit.SECONDS);
            return auths;
        }
        auths = JSONArray.parseArray(jsonString, Auth.class);
        return auths;
    }

    @Override
    public boolean updateById(User user, QueryWrapper<User> queryWrapper) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name",user.getUserName());
        User exist = baseMapper.selectOne(qw);
        if (exist != null){
            return false;
        }
        return baseMapper.update(user, queryWrapper) > 0;
    }

    @Override
    public List<Role> listUserRole(Integer userId) {
        return baseMapper.listUserRole(userId);
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
