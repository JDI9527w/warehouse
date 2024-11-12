package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.Result;
import com.learn.entity.Store;
import com.learn.mapper.StoreMapper;
import com.learn.service.ProductService;
import com.learn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
    @Autowired
    private ProductService productService;

    @Override
    public Result listByParam(Store store) {
        QueryWrapper<Store> qw = new QueryWrapper<>();
        if (store != null){
            if (store.getStoreName() != null && !store.getStoreName().equals("")) {
                qw.eq("store_name",store.getStoreName());
            }
            if (store.getStoreAddress()!= null && !store.getStoreAddress().equals("")) {
                qw.eq("store_address",store.getStoreAddress());
            }
            if (store.getConcat() != null && !store.getConcat().equals("")) {
                qw.eq("concat",store.getConcat());
            }
            if (store.getPhone() != null && !store.getPhone().equals("")) {
                qw.eq("phone",store.getPhone());
            }
        }
        List<Store> stores = baseMapper.selectList(qw);
        return Result.ok(stores);
    }
}
