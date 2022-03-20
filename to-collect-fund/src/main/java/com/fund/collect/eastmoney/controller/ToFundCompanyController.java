package com.fund.collect.eastmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: tbb
 * @Date: 2022/2/13 22:49
 * @Description: TFundCompanyController 消费者类
 */
@RestController
@RequestMapping("/eastmoney/company")
public class ToFundCompanyController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/list")
    public String list() {
        return restTemplate.getForObject("http://to-store-fund/eastmoney/company/list", String.class);
    }

}
