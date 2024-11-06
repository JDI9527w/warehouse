package com.learn.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库单表in_store表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStore {

    @TableId
    private Integer insId;//入库单id

    private Integer storeId;//仓库id

    private Integer productId;//商品id

    private Integer inNum;//入库数量

    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;//创建入库单的用户id
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;//创建时间

    private Integer isIn;//是否入库,1.是,0.否

    //-----------------追加的属性--------------------

    private String productName;//商品名称

    private String startTime;//起始时间

    private String endTime;//结束时间

    private String storeName;//仓库名称

    private String userCode;//创建入库单的用户的名称

    private BigDecimal inPrice;//商品入库价格
}
