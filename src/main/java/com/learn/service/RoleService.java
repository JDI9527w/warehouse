package com.learn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.AssignRoleAuthDTO;
import com.learn.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    /**
     * 角色列表
     * @param queryWrapper
     * @return
     */
    List<Role> listRole(QueryWrapper<Role> queryWrapper);

    /**
     * 通过角色iD删除角色
     *
     * @param roleId
     * @return
     */
    boolean removeRoleById(Integer roleId);

    /**
     * 给角色分配权限
     * @param araDTO
     * @return
     */
    boolean authGrantByRoleId(AssignRoleAuthDTO araDTO);
}
