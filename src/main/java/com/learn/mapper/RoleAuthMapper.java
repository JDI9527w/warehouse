package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.RoleAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleAuthMapper extends BaseMapper<RoleAuth> {

    // 删除用户角色下所有列表权限.
    boolean delByUserId(Integer userId);

    List<Integer> listAuthIdByRoleId(Integer roleId);
}
