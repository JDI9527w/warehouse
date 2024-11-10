package com.learn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsVO {
    private Integer storeId;
    private String storeName;
    private Integer totalInvent;
}
