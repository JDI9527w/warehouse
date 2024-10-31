package com.learn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> listRole(QueryWrapper<Role> queryWrapper);
}
