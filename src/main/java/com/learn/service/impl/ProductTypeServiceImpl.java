package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.entity.ProductType;
import com.learn.mapper.ProducttypeMapper;
import com.learn.service.ProductTypeService;
import com.learn.util.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProducttypeMapper, ProductType> implements ProductTypeService {

    @Override
    public List<ProductType> treeProductTypeList() {
        List<ProductType> typeList = baseMapper.selectList(new QueryWrapper<>());
        List<ProductType> treeTypeList = TreeUtil.makeTree(typeList, e -> e.getParentId() == 0, (x, y) -> x.getTypeId().equals(y.getParentId()), ProductType::setChildProductCategory);
        return treeTypeList;
    }
}
