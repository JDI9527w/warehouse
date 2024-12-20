package com.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 品牌表brand表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Brand implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer brandId;//品牌id

    private String brandName;//品牌名称

    private String brandLeter;//品牌首字母

    private String brandDesc;//品牌描述
}
