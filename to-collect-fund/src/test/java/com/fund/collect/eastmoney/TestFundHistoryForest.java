package com.fund.collect.eastmoney;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.collect.ToCollectFundApplication;
import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryLSJZ;
import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryResult;
import com.fund.collect.eastmoney.entity.list.EastMoneyListResult;
import com.fund.collect.eastmoney.forest.FundHistoryForest;
import com.fund.collect.eastmoney.forest.FundListForest;
import com.fund.entity.dto.EastMoneyListDataDto;
import com.fund.entity.result.GenericResult;
import com.fund.entity.result.PageResult;
import com.fund.entity.result.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @Author: tbb
 * @Date: 2022/2/13 23:14
 * @Description: 测试基金历史净值
 */
@SpringBootTest(classes = ToCollectFundApplication.class)
@Slf4j
public class TestFundHistoryForest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private FundHistoryForest fundHistoryForest;

    final static ExecutorService executorPool = new ThreadPoolExecutor(50, 50, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    AtomicLong result = new AtomicLong(0);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String fundCodeIndex = "fund-code";

    @Autowired
    FundListForest fundListForest;

    Integer pageSize = 100;

    Integer total = 0;

    @Test
    void getFundHistoryQuick() throws InterruptedException, ExecutionException {
        // 获取基金列表
        EastMoneyListResult fundList = fundListForest.getFundList();
        // List<EastMoneyListDataDto> resultFundList = new ArrayList<>();
        List<EastMoneyListDataDto> resultFundList = fundList.getData().stream().map(v -> {
            EastMoneyListDataDto e = new EastMoneyListDataDto();
            e.setFundCode(v.get(0));
            e.setFundName(v.get(2));
            e.setFundType(v.get(3));
            return e;
        }).collect(Collectors.toList());
        // resultFundList.clear();
        // EastMoneyListDataDto e = new EastMoneyListDataDto();
        // e.setFundCode("000002");
        // e.setFundName("000002");
        // e.setFundType("000002");
        // resultFundList.add(e);
        total = resultFundList.size();
        result.set(0);
        int i = 1;
        // 遍历基金列表
        for (EastMoneyListDataDto eastMoneyListDataDto : resultFundList) {
            executorPool.execute(new FundHistoryThreadQuick(eastMoneyListDataDto.getFundCode()));
            if (i%50 == 0){
                Thread.sleep(15000);
            }
            i++;
        }
        log.info("down: size:" + total);
    }

    class FundHistoryThreadQuick extends Thread {
        private String fundCode;

        public FundHistoryThreadQuick(String fundCode) {
            this.fundCode = fundCode;
        }

        @SneakyThrows
        @Override
        public void run() {
            Integer page = 0;
            try {
                EastmoneyHistoryResult eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, 1, pageSize);
                if(Objects.isNull(eastmoneyFundHistory) || eastmoneyFundHistory.getTotalCount() == 0){
                    log.info("fundCode:" + fundCode + ",go:" + result.get() + ",total:" + total + ",page:" + page);
                    result.addAndGet(1);
                    return;
                }
                List<EastMoneyHistoryLSJZConverter> historyLSJZConverters = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> getHistoryLSJZConverter(v, fundCode)).collect(Collectors.toList());
                // log.info(JSON.toJSONString(historyLSJZConverters, SerializerFeature.PrettyFormat));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<List<EastMoneyHistoryLSJZConverter>> request = new HttpEntity<>(historyLSJZConverters, headers);
                restTemplate.postForEntity("http://to-store-fund/eastmoney/fund_history/save_batch", request, Result.class);

                page = (eastmoneyFundHistory.getTotalCount() / pageSize) + ((eastmoneyFundHistory.getTotalCount() % pageSize > 0) ? 1 : 0);
                if (page > 0) {
                    for (int i = 0; i < (page - 1); i++) {
                        eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, i + 2, pageSize);
                        historyLSJZConverters = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> getHistoryLSJZConverter(v, fundCode)).collect(Collectors.toList());

                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        request = new HttpEntity<>(historyLSJZConverters, headers);
                        restTemplate.postForEntity("http://to-store-fund/eastmoney/fund_history/save_batch", request, Result.class);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error:" + fundCode + " : " + e.getMessage() + ",,,+" + page);
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : " + e.getMessage() + ",,,+" + page);
            }
            result.addAndGet(1);
            log.info("fundCode:" + fundCode + ",go:" + result.get() + ",total:" + total + ",page:" + page);
        }
    }

    /**
     * pojo转换类
     *
     * @param v
     * @return
     */
    public EastMoneyHistoryLSJZConverter getHistoryLSJZConverter(EastmoneyHistoryLSJZ v, String fundCode) {
        EastMoneyHistoryLSJZConverter c = new EastMoneyHistoryLSJZConverter();
        c.setFundCode(fundCode);
        c.setFundDate(LocalDate.parse(v.getFSRQ()));
        c.setUnitNet(v.getDWJZ());
        c.setSumNet(v.getLJJZ());
        c.setDayGrowRate(v.getJZZZL());
        c.setBuyStatus(v.getSGZT());
        c.setRedStatus(v.getSHZT());
        c.setDivide(v.getFHSP());
        c.setCreateDateTime(LocalDateTime.now());
        return c;
    }
}
