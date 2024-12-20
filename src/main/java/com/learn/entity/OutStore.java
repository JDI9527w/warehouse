package com.learn.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 出库单表out_store表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutStore {

    @TableId(type = IdType.AUTO)
    private Integer outsId;//出库单id

    private Integer productId;//出库单出库的商品id

    private Integer storeId;//出库单出库的商品所在仓库id

    private Integer tallyId;//理货员id

    private Double outPrice;//商品的出库价格

    private Integer outNum;//出库的商品数量
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;//创建出库单的用户id

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//创建出库单的时间

    private String isOut;//是否出库,0.未出库,1.已出库
}
