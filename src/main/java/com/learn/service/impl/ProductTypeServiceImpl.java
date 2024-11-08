package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.Result;
import com.learn.entity.ProductType;
import com.learn.mapper.ProductTypeMapper;
import com.learn.service.ProductTypeService;
import com.learn.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@CacheConfig(cacheNames="com.learn.service.impl.ProductTypeServiceImpl")
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Cacheable(key = "'all:productTypeTree'")
    @Override
    public List<ProductType> treeProductTypeList() {
        List<ProductType> typeList = baseMapper.selectList(new QueryWrapper<>());
        List<ProductType> treeTypeList = TreeUtil.makeTree(typeList, e -> e.getParentId() == 0, (x, y) -> x.getTypeId().equals(y.getParentId()), ProductType::setChildProductCategory);
        return treeTypeList;
    }

    @Override
    public Result addProductType(ProductType productType) {
        QueryWrapper<ProductType> qw = new QueryWrapper<>();
        qw.eq("type_name", productType.getTypeName());
        ProductType exist = baseMapper.selectOne(qw);
        return this.returnResult(productType,exist);
    }

    @Override
    public Result updateProductType(ProductType productType) {
        QueryWrapper<ProductType> qw = new QueryWrapper<>();
        qw.eq("type_name", productType.getTypeName());
        qw.ne("type_id",productType.getTypeId());
        ProductType exist = baseMapper.selectOne(qw);
        return this.returnResult(productType,exist);
    }

    @Override
    public Result deleteProductTypeById(Integer typeId) {
        List<Integer> list = baseMapper.listProductByTypeId(typeId);
        if (CollectionUtils.isEmpty(list)) {
            int i = baseMapper.deleteById(typeId);
            if (i == 1){
                redisTemplate.delete("com.learn.service.impl.ProductTypeServiceImpl::all:productTypeTree");
                return Result.ok("操作成功");
            }
            return Result.err(Result.CODE_ERR_SYS, "操作失败,系统错误");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"操作失败,有商品关联此分类,请删除商品或修改商品所属类别后再试");
    }

    public Result returnResult(ProductType productType,ProductType exist) {
        if (exist != null){
            return Result.err(Result.CODE_ERR_BUSINESS,"该分类名称已存在");
        }
        boolean b = this.saveOrUpdate(productType);
        if (b){
            redisTemplate.delete("com.learn.service.impl.ProductTypeServiceImpl::all:productTypeTree");
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS,"系统错误");
    }
}
