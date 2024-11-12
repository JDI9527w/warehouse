package com.learn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.InStoreDTO;
import com.learn.DTO.Result;
import com.learn.entity.InStore;
import org.apache.ibatis.annotations.Param;

public interface InStoreService extends IService<InStore> {
    Result pageInstoreList(@Param("inStoreDTO") InStoreDTO inStoreDTO, IPage<InStoreDTO> page);

    Result putInstore(InStore inStore);

    Result listByParam(InStoreDTO inStoreDTO);
}
