package com.learn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.ProductDTO;
import com.learn.DTO.Result;
import com.learn.entity.*;
import com.learn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RequestMapping("/product")
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
    @Autowired
    private ProductTypeService productTypeService;

    @Value("${file.upload-path}")
    private String fileUploadPath;

    /**
     * 分页查询商品
     *
     * @param productDTO
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/product-page-list")
    public Result pageProductList(ProductDTO productDTO,
                                  @RequestParam Integer pageSize,
                                  @RequestParam Integer pageNum) {
        IPage<ProductDTO> page = new Page<>(pageNum, pageSize);
        Page<ProductDTO> productDTOPage = productService.PageProductList(page, productDTO);
        return Result.ok(productDTOPage);
    }

    /**
     * 仓库列表查询
     *
     * @return
     */
    @GetMapping("/store-list")
    public Result listStores() {
        List<Store> storeList = storeService.list();
        return Result.ok(storeList);
    }

    /**
     * 供应商列表查询
     *
     * @return
     */
    @GetMapping("/supply-list")
    public Result listSupply() {
        List<Supply> supplyList = supplyService.list();
        return Result.ok(supplyList);
    }

    /**
     * 品牌列表查询
     *
     * @return
     */
    @GetMapping("/brand-list")
    public Result listBrand() {
        List<Brand> brandList = brandService.list();
        return Result.ok(brandList);
    }

    /**
     * 产地列表查询
     *
     * @return
     */
    @GetMapping("/place-list")
    public Result listPlace() {
        List<Place> placeList = placeService.list();
        return Result.ok(placeList);
    }

    /**
     * 单位列表查询
     *
     * @return
     */
    @GetMapping("/unit-list")
    public Result listUnit() {
        List<Unit> unitList = unitService.list();
        return Result.ok(unitList);
    }

    /**
     * @return
     */
    @GetMapping("/category-tree")
    public Result treeCategory() {
        List<ProductType> treeTypeList = productTypeService.treeProductTypeList();
        return Result.ok(treeTypeList);
    }

    @CrossOrigin
    @RequestMapping("/img-upload")
    public Result imgUpload(@RequestParam(value = "file") MultipartFile file) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 文件名
            String name = file.getName();
            // 带拓展名的文件名
            String originalFilename = file.getOriginalFilename();
            bis = new BufferedInputStream(file.getInputStream());
            // 将类路径解析为绝对路径.
            File fileDir = ResourceUtils.getFile(fileUploadPath);
            String absolutePath = fileDir.getAbsolutePath();
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File newFile = new File(absolutePath + '\\' + originalFilename);
            bos = new BufferedOutputStream(new FileOutputStream(newFile));
            byte[] bytes = new byte[1024 * 10];
            int readCount = 0;
            while ((readCount = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, readCount);
            }
            bos.flush();
            bis.close();
            bos.close();
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.err(Result.CODE_ERR_SYS, "上传失败");
    }

    @PostMapping("/product-add")
    public Result addProduct(@RequestBody Product product) {
        boolean save = productService.saveByProduct(product);
        if (save) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @PutMapping("/product-update")
    public Result updateProduct(@RequestBody Product product) {
        boolean flag = productService.updateByEntity(product);
        if (flag) {
            return Result.ok();
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @PutMapping("/state-change")
    public Result changeState(@RequestBody Product product) {
        boolean b = productService.updateById(product);
        if (b) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @DeleteMapping("/product-delete/{productId}")
    public Result deleteProductById(@PathVariable Integer productId) {
        boolean flag = productService.deleteProductById(productId);
        if (flag) {
            return Result.ok("操作成功");
        }
        return Result.err(Result.CODE_ERR_SYS, "操作失败");
    }

    @GetMapping("/exportTable")
    public Result exportProductList(ProductDTO productDTO) {
        return productService.listByParam(productDTO);
    }
}
