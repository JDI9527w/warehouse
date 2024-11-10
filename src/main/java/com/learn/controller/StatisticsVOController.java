package com.learn.controller;

import com.learn.DTO.Result;
import com.learn.service.StatisticsVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsVOController {

    @Autowired
    private StatisticsVOService statisticsVOService;

    @GetMapping("/store-invent")
    public Result storeInvent(){
        return Result.ok(statisticsVOService.storeInvent());
    }
}
