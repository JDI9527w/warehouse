package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.InStoreDTO;
import com.learn.entity.InStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InStoreMapper extends BaseMapper<InStore> {
    Page<InStoreDTO> queryInstorePageList(@Param("inStoreDTO") InStoreDTO inStoreDTO, IPage<InStoreDTO> page);

    List<InStoreDTO> listByParam(@Param("inStoreDTO") InStoreDTO inStoreDTO);
}
