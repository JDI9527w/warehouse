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
import java.time.LocalDateTime;

/**
 * 角色表的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {

    //角色id
    @TableId(type = IdType.AUTO)
    private Integer roleId;
    //角色名称
    private String roleName;
    //角色描述
    private String roleDesc;
    //角色标识
    private String roleCode;
    //角色状态
    private Integer roleState;
    //创建角色的用户id
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    //创建时间
    //json转换的日期格式
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //修改角色的用户id
    @TableField(fill = FieldFill.UPDATE)
    private Integer updateBy;
    //修改时间
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    //追加的属性--创建角色的用户的用户名
    @TableField(exist = false)
    private String getCode;
}
