package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.entity.ProductType;

import java.util.List;

public interface ProductTypeService extends IService<ProductType> {
    List<ProductType> treeProductTypeList();
}
