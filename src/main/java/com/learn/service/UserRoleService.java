package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.AssignRoleDto;
import com.learn.entity.UserRole;

import java.util.List;


public interface UserRoleService extends IService<UserRole> {

    boolean assignRoleByUserId(AssignRoleDto assignRoleDto);

    List<UserRole> listUserRoleByRoleId(Integer roleId);
}
