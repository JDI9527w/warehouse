package com.learn.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 商品表product表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer productId;//商品id

    private Integer storeId;//商品所在仓库id

    private Integer brandId;//商品所属品牌id

    private String productName;//商品名称

    private String productNum;//商品编码

    private Integer productInvent;//商品库存

    private Integer typeId;//商品所属分类id

    private Integer supplyId;//商品供应商id

    private Integer placeId;//商品产地id

    private Integer unitId;//商品单位id

    private String introduce;//商品介绍

    private String upDownState;//商品上下架状态,1.上架,0.下架

    private Double inPrice;//商品进价

    private Double salePrice;//商品售价

    private Double memPrice;//商品会员价

    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;//创建商品的用户id

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//商品的创建时间

    @TableField(fill = FieldFill.UPDATE)
    private Integer updateBy;//修改商品的用户id

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;//商品的修改时间

    private String imgs;//商品的图片地址

    private LocalDate productDate;//商品的生产日期

    private LocalDate suppDate;//商品的保质期
}
