package com.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.entity.Place;
import com.learn.mapper.PlaceMapper;
import com.learn.service.PlaceService;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements PlaceService {
}
