package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Role;
import com.learn.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getUserByCode(String userCode);

    List<User> listUsers(User user);

    List<Role> listUserRole(Integer userId);

    List<User> listUserExport();
}
