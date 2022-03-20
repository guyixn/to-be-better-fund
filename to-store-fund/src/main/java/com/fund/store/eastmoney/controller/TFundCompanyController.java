package com.fund.store.eastmoney.controller;


import com.fund.store.eastmoney.entity.TFundCompany;
import com.fund.store.eastmoney.service.ITFundCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 基金公司 前端控制器
 * </p>
 *
 * @author tbb
 * @since 2022-02-13
 */
@RestController
@RequestMapping("/eastmoney/company")
public class TFundCompanyController {

    @Autowired
    ITFundCompanyService itFundCompanyService;

    /**
     * 获取所有基金公司列表
     * @return
     */
    @GetMapping(value = "/list")
    public List<TFundCompany> list() {
        return itFundCompanyService.list();
    }



}
