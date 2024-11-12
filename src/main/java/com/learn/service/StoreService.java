package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.Result;
import com.learn.entity.Store;

public interface StoreService extends IService<Store> {
    Result listByParam(Store store);
}
