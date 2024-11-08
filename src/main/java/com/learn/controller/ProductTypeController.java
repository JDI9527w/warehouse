package com.learn.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.DTO.Result;
import com.learn.entity.ProductType;
import com.learn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 获取商品类别树
     *
     * @return
     */
    @GetMapping("/product-category-tree")
    public Result productTypeTree() {
        List<ProductType> typeList = productTypeService.treeProductTypeList();
        return Result.ok(typeList);
    }

    @GetMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode) {
        QueryWrapper<ProductType> qw = new QueryWrapper<>();
        qw.eq("type_code", typeCode);
        ProductType exist = productTypeService.getOne(qw);
        if (exist != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "该类型编码已存在");
        }
        return Result.ok();
    }

    @PostMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
        return productTypeService.addProductType(productType);
    }

    @PutMapping("/type-update")
    public Result updateProductType(@RequestBody ProductType productType) {
        return productTypeService.updateProductType(productType);
    }

    @DeleteMapping("/type-delete/{typeId}")
    public Result deleteProductTypeById(@PathVariable Integer typeId) {
        return productTypeService.deleteProductTypeById(typeId);
    }
}
