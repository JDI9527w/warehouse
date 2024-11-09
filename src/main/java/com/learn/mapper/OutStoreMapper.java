package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.OutStoreDTO;
import com.learn.entity.OutStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OutStoreMapper extends BaseMapper<OutStore> {
    Page<OutStoreDTO> pageOutStoreList(@Param("outStoreDTO") OutStoreDTO outStoreDTO, IPage<OutStoreDTO> page);
}
