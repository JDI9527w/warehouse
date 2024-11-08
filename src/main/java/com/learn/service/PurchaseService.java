package com.learn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.PurchaseDTO;
import com.learn.DTO.Result;
import com.learn.entity.Purchase;
import org.apache.ibatis.annotations.Param;

public interface PurchaseService extends IService<Purchase> {
    Result pageListPurchase(@Param("purchaseDTO") PurchaseDTO purchaseDTO, IPage<Purchase> page);

    Result updatePurchase(Purchase purchase);

    Result createInStore(Purchase purchase);
}
