package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Supply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupplyMapper extends BaseMapper<Supply> {
}