package com.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.OutStoreDTO;
import com.learn.DTO.Result;
import com.learn.entity.OutStore;
import com.learn.entity.Product;
import com.learn.mapper.OutStoreMapper;
import com.learn.service.OutStoreService;
import com.learn.service.ProductService;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutStoreServiceImpl extends ServiceImpl<OutStoreMapper, OutStore> implements OutStoreService {

    @Autowired
    private ProductService productService;

    /**
     * 分页查询
     *
     * @param outStoreDTO
     * @param page
     * @return
     */
    @Override
    public Result pageOutStoreList(OutStoreDTO outStoreDTO, IPage<OutStoreDTO> page) {
        Page<OutStoreDTO> pageList = baseMapper.pageOutStoreList(outStoreDTO, page);
        return Result.ok(pageList);
    }

    /**
     * 添加出库表
     *
     * @param outStore
     * @return
     */
    @Override
    public Result addOutStore(OutStore outStore) {
        QueryWrapper<OutStore> qw = new QueryWrapper<>();
        qw.eq("product_id", outStore.getProductId());
        qw.eq("is_out", WarehouseConstants.STATE_IS_NOTIN);
        List<OutStore> outStores = baseMapper.selectList(qw);
        Integer countNotOutProduct = outStores.stream().mapToInt(OutStore::getOutNum).sum();
        Integer productInvent = productService.getById(outStore.getProductId()).getProductInvent();
        if ((countNotOutProduct + outStore.getOutNum()) > productInvent) {
            return Result.err(Result.CODE_ERR_BUSINESS, "操作失败,总计未出库数量超过库存.");
        }
        outStore.setIsOut(WarehouseConstants.STATE_IS_NOTIN);
        boolean save = this.save(outStore);
        if (save) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败,系统错误.");
    }

    /**
     * 商品出库
     *
     * @param outStore
     * @return
     */
    @Override
    @Transactional
    public Result confirmOutStore(OutStore outStore) {
        outStore.setIsOut(WarehouseConstants.STATE_ISIN);
        Product product = productService.getById(outStore.getProductId());
        int nowNum = product.getProductInvent() - outStore.getOutNum();
        product.setProductInvent(nowNum);
        if (nowNum <= 0) {
            product.setUpDownState(WarehouseConstants.PRODUCT_DOWN_STATE);
        }
        boolean flagA = productService.updateById(product);
        boolean flagB = this.updateById(outStore);
        if (flagA && flagB) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @Override
    public Result listByParam(OutStoreDTO outStoreDTO) {
        return Result.ok(baseMapper.listByParam(outStoreDTO));
    }
}
