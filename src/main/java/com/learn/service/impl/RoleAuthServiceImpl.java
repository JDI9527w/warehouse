package com.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.entity.RoleAuth;
import com.learn.mapper.RoleAuthMapper;
import com.learn.service.RoleAuthService;
import org.springframework.stereotype.Service;

@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuth> implements RoleAuthService {
    @Override
    public boolean delByUserId(Integer userId) {
        return baseMapper.delByUserId(userId);
    }
}
