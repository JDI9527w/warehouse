package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper extends BaseMapper<Auth> {
    List<Auth> listUserAuthByUserId(int userId);

    List<Integer> listUserAuthIdByUserId(int userId);
}
