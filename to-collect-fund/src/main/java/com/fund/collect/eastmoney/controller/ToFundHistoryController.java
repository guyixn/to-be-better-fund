package com.fund.collect.eastmoney.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryLSJZ;
import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryResult;
import com.fund.collect.eastmoney.forest.FundHistoryForest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
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

    CountDownLatch cd = null;

    AtomicLong result = new AtomicLong(0);
    int total = 0;

    final static ExecutorService executorPool = new ThreadPoolExecutor(15, 15,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    @GetMapping(value = "/sync")
    void getAllFundHistorySync() throws InterruptedException {

//        Set<Object> keys = stringRedisTemplate.opsForHash().keys(fundCodeIndex);
//        Integer pageSize = 20;
//        cd = new CountDownLatch(keys.size());
//        total = keys.size();
//        result.set(0);
//        for (Object key : keys) {
//            String fundCode = (String) key;
//            executorPool.execute(new ToFundHistoryController.FundHistorySyncThread(fundCode, pageSize));
//        }
//        cd.await();
        log.info("test end");
    }


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
                List<EastMoneyHistoryLSJZConverter> resultList = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> getHistoryLSJZConverter(v, fundCode)).collect(Collectors.toList());
                ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:7004/eastmoney/fund_history/save", resultList, String.class);
                log.info(pageSize * pageIndex + "/" + eastmoneyFundHistory.getTotalCount() + "% -- " + responseEntity.getBody());
            } catch (Exception e) {
                log.error("Error f:" + e.getMessage());
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : " + pageIndex + " : " + e.getMessage());
            }
            Integer page = (eastmoneyFundHistory.getTotalCount() / pageSize) + ((eastmoneyFundHistory.getTotalCount() % pageSize > 0) ? 1 : 0);
            for (int i = 2; i <= page; i++) {
                eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, pageIndex, pageSize);
                List<EastMoneyHistoryLSJZConverter> resultList = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> getHistoryLSJZConverter(v, fundCode)).collect(Collectors.toList());
                ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:7004/eastmoney/fund_history/save", resultList, String.class);
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
    public EastMoneyHistoryLSJZConverter getHistoryLSJZConverter(EastmoneyHistoryLSJZ v, String fundCode){
        EastMoneyHistoryLSJZConverter c = new EastMoneyHistoryLSJZConverter();
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

    class FundHistorySyncThread extends Thread {

        private String fundCode;
        private Integer pageSize;

        public FundHistorySyncThread(String fundCode, Integer pageSize) {
            this.fundCode = fundCode;
            this.pageSize = pageSize;
        }

        @SneakyThrows
        @Override
        public void run() {
            try {
                EastmoneyHistoryResult eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, 1, pageSize);
                List<EastMoneyHistoryLSJZConverter> collects = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> {
                    return getHistoryLSJZConverter(v, fundCode);
                }).collect(Collectors.toList());
                stringRedisTemplate.opsForHash().put(fundCode,"1",JSON.toJSONString(collects));
                Integer page = (eastmoneyFundHistory.getTotalCount() / pageSize) + ((eastmoneyFundHistory.getTotalCount() % pageSize > 0) ? 1 : 0);
                for (int i = 2; i <= page; i++) {
                    EastmoneyHistoryResult eastmoneyFundHistory2 = fundHistoryForest.getFundHistory(fundCode, i, pageSize);
                    List<EastMoneyHistoryLSJZConverter> collects2 = eastmoneyFundHistory2.getData().getLSJZList().stream().map(v -> {
                        return getHistoryLSJZConverter(v, fundCode);
                    }).collect(Collectors.toList());
                    stringRedisTemplate.opsForHash().put(fundCode,String.valueOf(i),JSON.toJSONString(collects2));
                }
            } catch (Exception e) {
                log.error("Error:" + e.getMessage());
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : " + e.getMessage());
            }
            cd.countDown();
            result.addAndGet(1);
            log.info(result.get() + "/" + total + ",fundCode: " + fundCode);
        }
    }

}
