package com.fund.store.eastmoney.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.entity.dto.EastMoneyListDataDto;
import com.fund.entity.result.PageResult;
import com.fund.store.eastmoney.entity.TEmFundList;
import com.fund.store.eastmoney.service.ITEmFundListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * east money基金列表 前端控制器
 * </p>
 *
 * @author tbb
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/fund/list")
@Slf4j
public class TEmFundListController {

    @Autowired
    ITEmFundListService itEmFundListService;

    @RequestMapping(value = "/query")
    public PageResult query(@RequestBody EastMoneyListDataDto eastMoneyListDataDto){
        IPage<TEmFundList> pageCondition = new Page<>(eastMoneyListDataDto.getPage(), eastMoneyListDataDto.getPageSize());
        QueryWrapper<TEmFundList> qw = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(eastMoneyListDataDto.getFundCode())){
            qw.eq("fund_code",eastMoneyListDataDto.getFundCode());
        }
        qw.orderBy(true, "ascend".equals(eastMoneyListDataDto.getOrder()), "fund_code");
        IPage<TEmFundList> pageResult = itEmFundListService.page(pageCondition, qw);
        log.info(JSON.toJSONString(pageResult.getRecords()));
        return PageResult.success(pageResult);
    }
}
