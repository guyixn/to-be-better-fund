package com.fund.store.eastmoney.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.entity.result.Result;
import com.fund.store.eastmoney.entity.TFundHistoryNet;
import com.fund.store.eastmoney.service.ITFundHistoryNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eastmoney/fund/chart")
@Slf4j
public class TFundChartController {

    @Autowired
    ITFundHistoryNetService itFundHistoryNetService;

    @PostMapping(value = "/query")
    public Result save(@RequestBody EastMoneyHistoryLSJZConverter v) {
        QueryWrapper<TFundHistoryNet> qw = new QueryWrapper();
        qw.orderByAsc("fund_date");
        qw.eq("fund_code", "011322");
        // qw.gt("fund_date", "2022-06-01");
        return Result.success(itFundHistoryNetService.list(qw));
    }
}
