package com.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.entity.Supply;
import com.learn.mapper.SupplyMapper;
import com.learn.service.SupplyService;
import org.springframework.stereotype.Service;

@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements SupplyService {
}
