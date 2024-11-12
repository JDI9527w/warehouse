package com.learn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.Result;
import com.learn.entity.Store;
import com.learn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/store-page-list")
    public Result pageStoreList(Store store,
                                @RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        IPage<Store> page = new Page<>(pageNum, pageSize);
        // TODO:为什么传入空对象会生成查询条件?
//        QueryWrapper<Store> qw = new QueryWrapper<>(store);
        QueryWrapper<Store> qw = new QueryWrapper<>();
        if (store != null) {
            if (store.getStoreId() != null) {
                qw.eq("store_id", store.getStoreId());
            }
            if (store.getStoreName() != null && !store.getStoreName().equals("")) {
                qw.eq("store_name", store.getStoreName());
            }
            if (store.getConcat() != null && !store.getConcat().equals("")) {
                qw.eq("concat", store.getConcat());
            }
            if (store.getPhone() != null && !store.getPhone().equals("")) {
                qw.eq("phone", store.getPhone());
            }
        }
        return Result.ok(storeService.page(page, qw));
    }

    @GetMapping("/store-num-check")
    public Result checkStoreNum(String storeNum) {
        QueryWrapper<Store> qw = new QueryWrapper<>();
        qw.eq("store_num", storeNum);
        Store sotre = storeService.getOne(qw);
        if (sotre != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "编码重复");
        }
        return Result.ok();
    }

    @PostMapping("/store-add")
    public Result addStore(@RequestBody Store store) {
        boolean save = storeService.save(store);
        if (save) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @PutMapping("/store-update")
    public Result updateStore(@RequestBody Store store) {
        boolean flag = storeService.updateById(store);
        if (flag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @GetMapping("/exportTable")
    public Result exportRoleList(Store store) {
        return storeService.listByParam(store);
    }
}
