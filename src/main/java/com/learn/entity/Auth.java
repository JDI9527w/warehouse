package com.learn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * auth_info表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("auth_info")
public class Auth implements GrantedAuthority,Serializable {

	@TableId(type = IdType.AUTO)
	private int authId;//权限(菜单)id

	private int parentId;//父权限(菜单)id

	private String authName;//权限(菜单)名称

	private String authDesc;//权限(菜单)描述

	private int authGrade;//权限(菜单)层级

	private String authType;//权限(菜单)类型

	private String authUrl;//权限(菜单)访问的url接口

	private String authCode;//权限(菜单)标识

	private int authOrder;//权限(菜单)的优先级

	private Integer authState;//权限(菜单)状态(1.启用,0.禁用)

	@TableField(fill = FieldFill.INSERT)
	private Integer createBy;//创建权限(菜单)的用户id
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;//权限(菜单)的创建时间
	@TableField(fill = FieldFill.UPDATE)
	private Integer updateBy;//修改权限(菜单)的用户id
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;//权限(菜单)的修改时间

	//追加的List<Auth>集合属性 -- 用于存储当前权限(菜单)的子级权限(菜单)
	@TableField(exist = false)
	private List<Auth> childAuth;

	@Override
	public String getAuthority() {
		return null;
	}
}
