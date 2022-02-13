package com.store_fund.eastmoney.controller;


import com.store_fund.eastmoney.entity.TFundCompany;
import com.store_fund.eastmoney.service.ITFundCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
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
