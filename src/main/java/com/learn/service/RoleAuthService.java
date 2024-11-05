package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.entity.RoleAuth;

import java.util.List;

public interface RoleAuthService extends IService<RoleAuth> {
    boolean delByUserId(Integer userId);

    List<Integer> listAuthIdByRoleId(Integer roleId);

}
