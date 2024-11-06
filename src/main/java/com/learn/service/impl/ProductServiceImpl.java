package com.learn.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.ProductDTO;
import com.learn.entity.Product;
import com.learn.mapper.ProductMapper;
import com.learn.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public Page<ProductDTO> PageProductList(IPage<ProductDTO> page, @Param("productDTO")ProductDTO productDTO) {
        return baseMapper.PageProductList(page,productDTO);
    }
}
