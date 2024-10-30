package com.learn.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Auth;
import com.learn.entity.User;
import com.learn.mapper.UserMapper;
import com.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

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

    public List<Auth> treeAuth(List<Auth> auths) {
        List<Integer> parentIdList = new ArrayList<>();
        for (Auth auth : auths) {
            int parentId = auth.getParentId();
            if (parentId != 0) {
                if (parentIdList.contains(parentId)) {
                    continue;
                }
                parentIdList.add(parentId);
                List<Auth> children = auths.stream().filter(a -> a.getParentId() == parentId).collect(Collectors.toList());
                List<Auth> father = auths.stream().filter(a -> a.getAuthId() == parentId).collect(Collectors.toList());
                if (father.isEmpty()) {
                    continue;
                }
                Auth fatherAuth = father.get(0);
                fatherAuth.setChildAuth(children);
            }
        }
        auths = auths.stream().filter(auth -> auth.getParentId() == 0).collect(Collectors.toList());
        return auths;
    }

    @Override
    public boolean saveBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        return false;
    }

    @Override
    public User getOne(Wrapper<User> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<User> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<User> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<User> getBaseMapper() {
        return null;
    }

    @Override
    public Class<User> getEntityClass() {
        return null;
    }
}
