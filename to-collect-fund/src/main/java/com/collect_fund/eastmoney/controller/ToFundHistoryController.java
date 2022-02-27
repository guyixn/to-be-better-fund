package com.collect_fund.eastmoney.controller;

import cn.hutool.core.date.DateUtil;
import com.api_fund.EastmoneyHistoryLSJZConverter;
import com.collect_fund.eastmoney.entity.fund_history.EastmoneyHistoryLSJZ;
import com.collect_fund.eastmoney.entity.fund_history.EastmoneyHistoryResult;
import com.collect_fund.eastmoney.forest.FundHistoryForest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ToFundHistoryController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FundHistoryForest fundHistoryForest;

    private String fundCodeIndex = "fund-code";

    @GetMapping(value = "/save")
    public String list() throws InterruptedException {

        AtomicLong result = new AtomicLong(0);

        Set<Object> keys = stringRedisTemplate.opsForHash().keys(fundCodeIndex);
        Integer total = keys.size();
        Integer pageSize = 20;
        for (Object key : keys) {
            result.set(0);
            String fundCode = (String) key;
            EastmoneyHistoryResult eastmoneyFundHistory = null;
            Integer pageIndex = 1;
            try {
                eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, pageIndex, pageSize);
                List<EastmoneyHistoryLSJZConverter> resultList = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> getHistoryLSJZConverter(v, fundCode)).collect(Collectors.toList());
                ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://to-store-fund/eastmoney/fund_history/save", resultList, String.class);
                log.info(pageSize * pageIndex + "/" + eastmoneyFundHistory.getTotalCount() + "% -- " + responseEntity.getBody());
            } catch (Exception e) {
                log.error("Error f:" + e.getMessage());
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : " + pageIndex + " : " + e.getMessage());
            }
            Integer page = (eastmoneyFundHistory.getTotalCount() / pageSize) + ((eastmoneyFundHistory.getTotalCount() % pageSize > 0) ? 1 : 0);
            for (int i = 2; i <= page; i++) {
                eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, pageIndex, pageSize);
                List<EastmoneyHistoryLSJZConverter> resultList = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> getHistoryLSJZConverter(v, fundCode)).collect(Collectors.toList());
                ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://to-store-fund/eastmoney/fund_history/save", resultList, String.class);
                log.info(pageSize * pageIndex + "/" + eastmoneyFundHistory.getTotalCount() + "% -- " + responseEntity.getBody());
            }
            result.addAndGet(1);
            log.info(result + "/" + total + " == fundCode: " + fundCode + ",result size:" + eastmoneyFundHistory.getTotalCount());
        }
        return "success";
    }

    /**
     * pojo转换类
     * @param v
     * @return
     */
    public EastmoneyHistoryLSJZConverter getHistoryLSJZConverter(EastmoneyHistoryLSJZ v, String fundCode){
        EastmoneyHistoryLSJZConverter c = new EastmoneyHistoryLSJZConverter();
        c.setFundCode(fundCode);
        c.setFundDate(v.getFSRQ());
        c.setUnitNet(v.getDWJZ());
        c.setSumNet(v.getLJJZ());
        c.setDayGrowRate(v.getJZZZL());
        c.setBuyStatus(v.getSGZT());
        c.setRedStatus(v.getSHZT());
        c.setDivide(v.getFHSP());
        c.setCreateDate(DateUtil.today());
        return c;
    }

}
