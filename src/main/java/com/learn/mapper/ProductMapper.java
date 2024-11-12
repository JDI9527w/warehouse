package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.ProductDTO;
import com.learn.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    Page<ProductDTO> PageProductList(IPage<ProductDTO> page, @Param("productDTO")ProductDTO productDTO);

    List<ProductDTO> listByParam(@Param("productDTO")ProductDTO productDTO);
}
