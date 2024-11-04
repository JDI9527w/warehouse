package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.RoleAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleAuthMapper extends BaseMapper<RoleAuth> {

    // 删除用户角色下所有列表权限.
    boolean delByUserId(Integer userId);
}
