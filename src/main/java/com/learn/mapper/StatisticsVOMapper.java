package com.learn.mapper;

import com.learn.vo.StatisticsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsVOMapper {
    List<StatisticsVO> storeInvent();
}
