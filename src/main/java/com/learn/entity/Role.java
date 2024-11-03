package com.learn.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {

    //角色id
    private Integer roleId;
    //角色名称
    private String roleName;
    //角色描述
    private String roleDesc;
    //角色标识
    private String roleCode;
    //角色状态
    private String roleState;
    //创建角色的用户id
    @TableField(fill = FieldFill.INSERT)
    private int createBy;
    //创建时间
    //json转换的日期格式
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //修改角色的用户id
    @TableField(fill = FieldFill.UPDATE)
    private int updateBy;
    //修改时间
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //追加的属性--创建角色的用户的用户名
    @TableField(exist = false)
    private String getCode;
}
