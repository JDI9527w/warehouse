package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Place;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceMapper extends BaseMapper<Place> {
}
