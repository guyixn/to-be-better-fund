package com.fund.store.eastmoney.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.store.eastmoney.entity.TFundHistoryNet;
import com.fund.store.eastmoney.service.ITFundHistoryNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String save(@RequestBody EastMoneyHistoryLSJZConverter v) {
        TFundHistoryNet t = new TFundHistoryNet();
        t.setFundCode(v.getFundCode());
        t.setFundDate(DateUtil.parseDate(v.getFundDate()));
        if (!StrUtil.isBlank(v.getUnitNet()) && !"--".equals(v.getUnitNet())) {
            t.setUnitNet(Double.parseDouble(v.getUnitNet()));
        }
        if (!StrUtil.isBlank(v.getSumNet()) && !"--".equals(v.getSumNet())) {
            t.setSumNet(Double.parseDouble(v.getSumNet()));
        }
        if (!StrUtil.isBlank(v.getDayGrowRate()) && !"--".equals(v.getDayGrowRate())){
            t.setDayGrowRate(Double.parseDouble(v.getDayGrowRate()));
        }
        t.setBuyStatus(v.getBuyStatus());
        t.setRedStatus(v.getRedStatus());
        t.setDivide(v.getDivide());
        t.setCreateDate(DateUtil.parseDate(v.getCreateDate()));
        boolean isSucc = itFundHistoryNetService.save(t);
        if (isSucc) {
            return "true";
        } else {
            return "error";
        }
    }

    @PostMapping(value = "/save_batch")
    public String saveBatch(@RequestBody List<EastMoneyHistoryLSJZConverter> es) {
        List<TFundHistoryNet> cs = es.stream().map(v -> {
            TFundHistoryNet t = new TFundHistoryNet();
            t.setFundCode(v.getFundCode());
            t.setFundDate(DateUtil.parseDate(v.getFundDate()));
            if (!StrUtil.isBlank(v.getUnitNet()) && !"--".equals(v.getUnitNet())) {
                t.setUnitNet(Double.parseDouble(v.getUnitNet()));
            }
            if (!StrUtil.isBlank(v.getSumNet()) && !"--".equals(v.getSumNet())) {
                t.setSumNet(Double.parseDouble(v.getSumNet()));
            }
            if (!StrUtil.isBlank(v.getDayGrowRate()) && !"--".equals(v.getDayGrowRate())){
                t.setDayGrowRate(Double.parseDouble(v.getDayGrowRate()));
            }
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
