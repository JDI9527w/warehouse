package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.Result;
import com.learn.entity.ProductType;

import java.util.List;

public interface ProductTypeService extends IService<ProductType> {
    List<ProductType> treeProductTypeList();

    Result addProductType(ProductType productType);

    Result updateProductType(ProductType productType);

    Result deleteProductTypeById(Integer typeId);
}
