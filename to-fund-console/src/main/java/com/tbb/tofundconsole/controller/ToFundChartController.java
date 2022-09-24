package com.tbb.tofundconsole.controller;

import com.alibaba.fastjson.JSON;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.entity.dto.EastMoneyListDataDto;
import com.fund.entity.result.GenericResult;
import com.fund.entity.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: tbb
 * @Date: 2022/2/14 0:56
 * @Description: 基金排名数据信息
 */
@RestController
@RequestMapping("/api/fund/chart")
@Slf4j
public class ToFundChartController<T> {

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/query")
    public T query(@RequestBody EastMoneyHistoryLSJZConverter eastMoneyListDataDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EastMoneyHistoryLSJZConverter> request = new HttpEntity<>(eastMoneyListDataDto, headers);
        ResponseEntity<Result> result = restTemplate.postForEntity("http://to-store-fund/eastmoney/fund/chart/query", request, Result.class);
        log.info("The response is {}", JSON.toJSONString(result));
        return GenericResult.success(result.getBody());
        // return Result.success(restTemplate.postForObject("http://to-store-fund/fund/list/query", eastMoneyListDataDto, EastMoneyListDataDto.class));
    }
}
