package com.learn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.PurchaseDTO;
import com.learn.DTO.Result;
import com.learn.entity.Purchase;
import com.learn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/purchase-page-list")
    public Result purchasePageList(PurchaseDTO purchaseDTO,
                                   @RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize) {
        IPage<Purchase> page = new Page<>(pageNum, pageSize);
        return purchaseService.pageListPurchase(purchaseDTO, page);
    }

    @PostMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase) {
        purchase.setIsIn("0");
        boolean save = purchaseService.save(purchase);
        if (save) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @PutMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase) {
        return purchaseService.updatePurchase(purchase);
    }

    @PostMapping("/in-warehouse-record-add")
    public Result createInStore(@RequestBody Purchase purchase) {
        return purchaseService.createInStore(purchase);
    }

    @GetMapping("/exportTable")
    public Result exportRoleList(PurchaseDTO purchaseDTO) {
        return purchaseService.listByParam(purchaseDTO);
    }
}
