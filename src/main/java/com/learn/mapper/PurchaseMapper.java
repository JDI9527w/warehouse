package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.ProductDTO;
import com.learn.DTO.PurchaseDTO;
import com.learn.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseMapper extends BaseMapper<Purchase> {
    Page<ProductDTO> pageListPurchase(@Param("purchaseDTO")PurchaseDTO purchaseDTO, IPage<Purchase> page);
}
