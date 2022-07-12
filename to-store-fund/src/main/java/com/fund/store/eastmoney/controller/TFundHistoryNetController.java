package com.fund.store.eastmoney.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.entity.result.Result;
import com.fund.store.eastmoney.entity.TFundHistoryNet;
import com.fund.store.eastmoney.service.ITFundHistoryNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
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
@Slf4j
public class TFundHistoryNetController {

    @Autowired
    ITFundHistoryNetService itFundHistoryNetService;

    @PostMapping(value = "/save")
    public Result save(@RequestBody EastMoneyHistoryLSJZConverter v) {
        TFundHistoryNet t = new TFundHistoryNet();
        t.setFundCode(v.getFundCode());
        ZoneId zone = ZoneId.systemDefault();
        Instant instantDate = v.getFundDate().atStartOfDay().atZone(zone).toInstant();
        t.setFundDate(Date.from(instantDate));
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
        t.setFundShortName(v.getFundShortName());
        Instant instantDateTime = v.getCreateDateTime().atZone(zone).toInstant();
        t.setCreateDateTime(Date.from(instantDateTime));
        log.info(JSON.toJSONString(t));
        QueryWrapper<TFundHistoryNet> qw = new QueryWrapper();
        qw.eq("fund_code", t.getFundCode());
        qw.eq("fund_date", DateUtil.format(t.getFundDate(), DatePattern.NORM_DATE_PATTERN));
        long count = itFundHistoryNetService.count(qw);
        if (count == 0){
            return Result.success(itFundHistoryNetService.save(t));
        }else{
            return Result.success();
        }
    }

    @PostMapping(value = "/save_batch")
    public Result saveBatch(@RequestBody List<EastMoneyHistoryLSJZConverter> es) {
        if (Objects.isNull(es) || es.isEmpty()){
            return Result.failed();
        }

        List<TFundHistoryNet> cs = es.stream().map(v -> {
            TFundHistoryNet t = new TFundHistoryNet();
            t.setFundCode(v.getFundCode());
            ZoneId zone = ZoneId.systemDefault();
            Instant instantDate = v.getFundDate().atStartOfDay().atZone(zone).toInstant();
            t.setFundDate(Date.from(instantDate));
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

            Instant instantDateTime = v.getCreateDateTime().atZone(zone).toInstant();
            t.setCreateDateTime(Date.from(instantDateTime));
            // itFundHistoryNetService.save(t);
            return t;
        }).collect(Collectors.toList());
        // log.info(JSON.toJSONString(cs,SerializerFeature.PrettyFormat));
        return Result.success(itFundHistoryNetService.saveBatch(cs));
    }
}
