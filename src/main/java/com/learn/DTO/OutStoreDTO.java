package com.learn.DTO;

import com.learn.entity.OutStore;
import lombok.*;

/**
 * 出库单表out_store表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutStoreDTO extends OutStore {
    private String productName;//商品名称

    private String startTime;//起始时间

    private String endTime;//结束时间

    private String storeName;//仓库名称

    private String userCode;//创建出库单的用户的名称
}
