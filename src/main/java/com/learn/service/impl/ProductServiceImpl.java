package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.ProductDTO;
import com.learn.DTO.Result;
import com.learn.entity.Product;
import com.learn.mapper.ProductMapper;
import com.learn.service.ProductService;
import com.learn.util.WarehouseConstants;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Value("${file.access-path}")
    private String accessPath;
    @Value("${file.upload-path}")
    private String fileUploadPath;

    @Override
    public Page<ProductDTO> PageProductList(IPage<ProductDTO> page, @Param("productDTO") ProductDTO productDTO) {
        return baseMapper.PageProductList(page, productDTO);
    }

    @Override
    public boolean saveByProduct(Product product) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_name", product.getProductName());
        Product exist = baseMapper.selectOne(queryWrapper);
        if (exist == null) {
            product.setImgs(accessPath + "\\" + product.getImgs());
            product.setUpDownState(WarehouseConstants.PRODUCT_DOWN_STATE);
            int insert = baseMapper.insert(product);
            return insert == 1;
        }
        return false;
    }

    @Override
    public boolean updateByEntity(Product product) {
        try {
            Integer productId = product.getProductId();
            Product select = baseMapper.selectOne(new QueryWrapper<Product>().eq("product_id", productId));
            String imgs = select.getImgs().replace(accessPath,"");
            Path path = Paths.get(ResourceUtils.getFile(fileUploadPath).getAbsolutePath() + imgs);
            Files.deleteIfExists(path);
            product.setImgs(accessPath + "/" + product.getImgs());
            product.setUpDownState(WarehouseConstants.PRODUCT_DOWN_STATE);
            int i = baseMapper.updateById(product);
            return i == 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProductById(Integer productId) {
        try {
            Product select = baseMapper.selectOne(new QueryWrapper<Product>().eq("product_id", productId));
            String imgs = select.getImgs().replace(accessPath,"");
            Path path = Paths.get(ResourceUtils.getFile(fileUploadPath).getAbsolutePath() + imgs);
            Files.deleteIfExists(path);
            int i = baseMapper.deleteById(productId);
            return i == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Result listByParam(ProductDTO productDTO) {
        List<ProductDTO> productList = baseMapper.listByParam(productDTO);
        return Result.ok(productList);
    }
}
