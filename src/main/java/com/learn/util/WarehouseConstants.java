package com.learn.util;

/**
 * 常量类:
 */
public interface WarehouseConstants {

    //用户未审核
    public Integer USER_STATE_NOT_PASS = 0;

    //用户已审核
    public Integer USER_STATE_PASS = 1;

    // 逻辑未删除
    public Integer LOGIC_NOT_DELETE_VALUE = 0;

    // 逻辑删除
    public Integer LOGIC_DELETE_VALUE = 1;
    // 禁用
    public Integer STATE_DISABLED = 0;
    // 启用
    public Integer STATE_ENABLED = 1;

    //传递token的请求头名称
    public String HEADER_TOKEN_NAME = "Token";
}
