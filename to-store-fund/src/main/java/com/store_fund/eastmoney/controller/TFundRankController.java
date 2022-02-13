package com.store_fund.eastmoney.controller;


import com.store_fund.eastmoney.entity.TFundRank;
import com.store_fund.eastmoney.service.ITFundRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 排名 前端控制器
 * </p>
 *
 * @author tbb
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/eastmoney/fund_rank")
public class TFundRankController {

    @Autowired
    ITFundRankService itFundRankService;

    @PostMapping(value="/save", consumes ="application/json")
    public String insertData(@RequestBody List<TFundRank> tFundRank){
        boolean isSucc =  itFundRankService.saveBatch(tFundRank);
        if (isSucc){
            return "true";
        }else{
            return "error";
        }
    }

    @GetMapping("/list")
    public List<TFundRank> list(){
        return itFundRankService.list();
    }
}
