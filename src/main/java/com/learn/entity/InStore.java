package com.learn.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 入库单表in_store表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStore {

    @TableId(type = IdType.AUTO)
    private Integer insId;//入库单id

    private Integer storeId;//仓库id

    private Integer productId;//商品id

    private Integer inNum;//入库数量
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;//创建入库单的用户id
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//创建时间

    private String isIn;//是否入库,1.是,0.否

    public InStore(Integer storeId, Integer productId, Integer inNum,  String isIn) {
        this.storeId = storeId;
        this.productId = productId;
        this.inNum = inNum;
        this.isIn = isIn;
    }
}
