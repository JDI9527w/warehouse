package com.learn.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.DTO.InStoreDTO;
import com.learn.DTO.Result;
import com.learn.entity.InStore;
import com.learn.entity.Product;
import com.learn.mapper.InStoreMapper;
import com.learn.service.InStoreService;
import com.learn.service.ProductService;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InStoreServiceImpl extends ServiceImpl<InStoreMapper, InStore> implements InStoreService {
    @Autowired
    private ProductService productService;

    @Override
    public Result pageInstoreList(InStoreDTO inStoreDTO, IPage<InStoreDTO> page) {
        Page<InStoreDTO> pages = baseMapper.queryInstorePageList(inStoreDTO, page);
        return Result.ok(pages);
    }

    /**
     * 确认入库
     *
     * @param inStore
     * @return
     */
    @Override
    @Transactional
    public Result putInstore(InStore inStore) {
        Product product = productService.getById(inStore.getProductId());
        product.setProductInvent(product.getProductInvent() + inStore.getInNum());
        inStore.setIsIn(WarehouseConstants.STATE_ISIN);
        boolean flag = productService.updateById(product);
        if (flag) {
            boolean flag2 = updateById(inStore);
            if (flag2) {
                return Result.ok("操作成功");
            }
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败,系统错误");
    }

    @Override
    public Result listByParam(InStoreDTO inStoreDTO) {
        return Result.ok(baseMapper.listByParam(inStoreDTO));
    }
}
