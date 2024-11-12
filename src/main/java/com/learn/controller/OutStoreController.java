package com.learn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.DTO.CurrentUser;
import com.learn.DTO.OutStoreDTO;
import com.learn.DTO.Result;
import com.learn.entity.OutStore;
import com.learn.service.OutStoreService;
import com.learn.util.TokenUtils;
import com.learn.util.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/outstore")
public class OutStoreController {
    @Autowired
    private OutStoreService outStoreService;
    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/outstore-page-list")
    public Result pageOutStroeList(OutStoreDTO outStoreDTO,
                                   @RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize) {
        IPage<OutStoreDTO> page = new Page<>(pageNum, pageSize);
        return outStoreService.pageOutStoreList(outStoreDTO, page);
    }

    @PostMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore, HttpServletRequest request) {
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        outStore.setTallyId(currentUser.getUserId());
        return outStoreService.addOutStore(outStore);
    }

    @PutMapping("/outstore-confirm")
    public Result confirmOutStore(@RequestBody OutStore outStore) {
        return outStoreService.confirmOutStore(outStore);
    }

    @GetMapping("/exportTable")
    public Result exportRoleList(OutStoreDTO outStoreDTO) {
        return outStoreService.listByParam(outStoreDTO);
    }
}
