package com.learn.DTO;

import com.learn.entity.InStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 入库单表in_store表的数据传输类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStoreDTO extends InStore {
    //-----------------追加的属性--------------------

    private String productName;//商品名称

    private String startTime;//起始时间

    private String endTime;//结束时间

    private String storeName;//仓库名称

    private String userCode;//创建入库单的用户的名称

    private BigDecimal inPrice;//商品入库价格
}
