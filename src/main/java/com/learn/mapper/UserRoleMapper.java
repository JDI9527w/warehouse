package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    Integer insertUserRoleBatch(List<UserRole> userRoleList);

    /**
     * 删除对应userID的授权信息.
     * @param userId
     * @return
     */
    int delUserRoleByUserID(Integer userId);
}
