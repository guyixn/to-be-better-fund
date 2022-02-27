package com.store_fund.eastmoney.controller;


import cn.hutool.core.date.DateUtil;
import com.api_fund.EastmoneyHistoryLSJZConverter;
import com.store_fund.eastmoney.entity.TFundHistoryNet;
import com.store_fund.eastmoney.entity.TFundRank;
import com.store_fund.eastmoney.service.ITFundHistoryNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tbb
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/eastmoney/fund_history")
public class TFundHistoryNetController {

    @Autowired
    ITFundHistoryNetService itFundHistoryNetService;

    @PostMapping(value = "/save")
    public String insertData(@RequestBody List<EastmoneyHistoryLSJZConverter> es) {
        List<TFundHistoryNet> cs = es.stream().map(v -> {
            TFundHistoryNet t = new TFundHistoryNet();
            t.setFundCode(v.getFundCode());
            t.setFundDate(DateUtil.parseDate(v.getFundDate()));
            t.setUnitNet(Double.parseDouble(v.getUnitNet()));
            t.setSumNet(Double.parseDouble(v.getSumNet()));
            t.setDayGrowRate(Double.parseDouble(v.getDayGrowRate()));
            t.setBuyStatus(v.getBuyStatus());
            t.setRedStatus(v.getRedStatus());
            t.setDivide(v.getDivide());
            t.setCreateDate(DateUtil.parseDate(v.getCreateDate()));
            return t;
        }).collect(Collectors.toList());
        boolean isSucc = itFundHistoryNetService.saveBatch(cs);
        if (isSucc) {
            return "true";
        } else {
            return "error";
        }
    }
}
