package com.learn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.InStoreDTO;
import com.learn.DTO.Result;
import com.learn.service.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instore")
public class InStoreController {
    @Autowired
    private InStoreService inStoreService;

    @GetMapping("/instore-page-list")
    public Result pageInStoreList(InStoreDTO inStoreDTO,
                                  @RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize) {
        IPage<InStoreDTO> page = new Page<>(pageNum, pageSize);
        return inStoreService.pageInstoreList(inStoreDTO, page);
    }

    @PutMapping("/instore-confirm")
    public Result instoreConfirm(@RequestBody InStoreDTO inStoreDTO) {
        Result result = inStoreService.putInstore(inStoreDTO);
        return result;
    }
}
