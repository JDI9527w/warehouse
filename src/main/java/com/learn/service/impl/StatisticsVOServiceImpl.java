package com.learn.service.impl;

import com.learn.mapper.StatisticsVOMapper;
import com.learn.service.StatisticsVOService;
import com.learn.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsVOServiceImpl implements StatisticsVOService {
    @Autowired
    private StatisticsVOMapper statisticsVOMapper;

    @Override
    public List<StatisticsVO> storeInvent() {
        return statisticsVOMapper.storeInvent();
    }
}
