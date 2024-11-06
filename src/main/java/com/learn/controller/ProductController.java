package com.learn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.ProductDTO;
import com.learn.DTO.Result;
import com.learn.entity.*;
import com.learn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SupplyService supplyService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private UnitService unitService;

    /**
     * 分页查询商品
     * @param productDTO
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/product/product-page-list")
    public Result pageProductList(@RequestBody(required = false) ProductDTO productDTO,
                                  @RequestParam Integer pageSize,
                                  @RequestParam Integer pageNum) {
        IPage<ProductDTO> page = new Page<>(pageNum, pageSize);
        Page<ProductDTO> productDTOPage = productService.PageProductList(page, productDTO);
        return Result.ok(productDTOPage);
    }

    /**
     * 仓库列表查询
     * @return
     */
    @GetMapping("/product/store-list")
    public Result listStores() {
        List<Store> storeList = storeService.list();
        return Result.ok(storeList);
    }

    /**
     * 供应商列表查询
     * @return
     */
    @GetMapping("/product/supply-list")
    public Result listSupply() {
        List<Supply> supplyList = supplyService.list();
        return Result.ok(supplyList);
    }

    /**
     * 品牌列表查询
     * @return
     */
    @GetMapping("/product/brand-list")
    public Result listBrand() {
        List<Brand> brandList = brandService.list();
        return Result.ok(brandList);
    }

    /**
     * 产地列表查询
     * @return
     */
    @GetMapping("/product/place-list")
    public Result listPlace() {
        List<Place> placeList = placeService.list();
        return Result.ok(placeList);
    }

    /**
     * 单位列表查询
     * @return
     */
    @GetMapping("/product/unit-list")
    public Result listUnit() {
        List<Unit> unitList = unitService.list();
        return Result.ok(unitList);
    }

    /**
     *
     * @return
     */
    @GetMapping("/product/category-tree")
    public Result treeCategory() {

        return Result.ok();
    }
}
