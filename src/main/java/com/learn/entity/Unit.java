package com.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 单位表unit表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Unit implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer unitId;//单位id

    private String unitName;//单位

    private String unitDesc;//单位描述
}
