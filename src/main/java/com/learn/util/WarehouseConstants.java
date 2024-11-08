package com.learn.util;

/**
 * 常量类:
 */
 public interface WarehouseConstants {

    //用户未审核
     Integer USER_STATE_NOT_PASS = 0;

    //用户已审核
     Integer USER_STATE_PASS = 1;

    // 逻辑未删除
     Integer LOGIC_NOT_DELETE_VALUE = 0;

    // 逻辑删除
     Integer LOGIC_DELETE_VALUE = 1;
    // 禁用
     Integer STATE_DISABLED = 0;
    // 启用
     Integer STATE_ENABLED = 1;
    // 未入库/未生成
     String STATE_IS_NOTIN = "0";
    // 入库/生成入库单
     String STATE_ISIN = "1";
    //商品下架
     String PRODUCT_DOWN_STATE = "0";
    // 商品上架
     String PRODUCT_UP_STATE = "1";
    //传递token的请求头名称
     String HEADER_TOKEN_NAME = "Token";
}
