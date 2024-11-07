package com.learn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.ProductDTO;
import com.learn.entity.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductService extends IService<Product> {
    Page<ProductDTO> PageProductList(IPage<ProductDTO> page, @Param("productDTO")ProductDTO productDTO);

    boolean saveByProduct(Product product);

    boolean updateByEntity(Product product);

    boolean deleteProductById(Integer productId);

}
