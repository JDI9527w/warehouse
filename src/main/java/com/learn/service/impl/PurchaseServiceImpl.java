package com.learn.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.ProductDTO;
import com.learn.DTO.PurchaseDTO;
import com.learn.DTO.Result;
import com.learn.entity.InStore;
import com.learn.entity.Purchase;
import com.learn.mapper.PurchaseMapper;
import com.learn.service.InStoreService;
import com.learn.service.PurchaseService;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    @Autowired
    private InStoreService inStoreService;
    /**
     * 采购单分页查询
     * @param purchaseDTO
     * @param page
     * @return
     */
    @Override
    public Result pageListPurchase(PurchaseDTO purchaseDTO, IPage<Purchase> page) {
        Page<ProductDTO> purchaseList = baseMapper.pageListPurchase(purchaseDTO, page);
        return Result.ok(purchaseList);
    }

    /**
     * 更改采购单
     *
     * @param purchase
     * @return
     */
    @Override
    public Result updatePurchase(Purchase purchase) {
        purchase.setBuyTime(new Date());
        int i = baseMapper.updateById(purchase);
        if (i == 1) {
            Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    /**
     * 生成入库单
     *
     * @param purchase
     * @return
     */
    @Override
    @Transactional
    public Result createInStore(Purchase purchase) {
        purchase.setIsIn(WarehouseConstants.STATE_ISIN);
        baseMapper.updateById(purchase);
        InStore inStore = new InStore(purchase.getStoreId(),
                purchase.getProductId(),
                purchase.getFactBuyNum(),
                WarehouseConstants.STATE_IS_NOTIN);
        boolean save = inStoreService.save(inStore);
        if (save) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS,"操作失败");
    }
}
