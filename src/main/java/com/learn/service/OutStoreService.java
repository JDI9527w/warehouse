package com.learn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.OutStoreDTO;
import com.learn.DTO.Result;
import com.learn.entity.OutStore;

public interface OutStoreService extends IService<OutStore> {
    Result pageOutStoreList(OutStoreDTO outStoreDTO, IPage<OutStoreDTO> page);

    Result addOutStore(OutStore outStore);

    Result confirmOutStore(OutStore outStore);

    Result listByParam(OutStoreDTO outStoreDTO);
}
