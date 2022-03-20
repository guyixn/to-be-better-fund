package com.fund.collect.eastmoney;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.collect.ToCollectFundApplication;
import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryLSJZ;
import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryResult;
import com.fund.collect.eastmoney.entity.list.EastMoneyListData;
import com.fund.collect.eastmoney.entity.list.EastMoneyListResult;
import com.fund.collect.eastmoney.forest.FundHistoryForest;
import com.fund.collect.eastmoney.forest.FundListForest;
import com.fund.collect.eastmoney.forest.FundStoreForest;
import com.fund.collect.liteflow.slot.DailySlot;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.*;
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
    private FundHistoryForest fundHistoryForest;

    final static ExecutorService executorPool = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    final static ExecutorService executorPoolNext = new ThreadPoolExecutor(15, 15,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    AtomicLong result = new AtomicLong(0);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String fundCodeIndex = "fund-code";

    @Autowired
    FundStoreForest fundStoreForest;

    @Autowired
    FundListForest fundListForest;

    Integer pageSize = 20;

    Integer total = 0;

    @Test
    void getFundHistoryQuick() throws InterruptedException, ExecutionException {
        //获取基金列表
        EastMoneyListResult fundList = fundListForest.getFundList();
        List<EastMoneyListData> resultFundList = fundList.getData().stream().map(v -> {
            EastMoneyListData e = new EastMoneyListData();
            e.setFundCode(v.get(0));
            e.setFundName(v.get(2));
            e.setFundType(v.get(3));
            return e;
        }).collect(Collectors.toList());
        total = resultFundList.size();
        result.set(0);
        List<FutureTask<Long>> exPoolList = new ArrayList<>();
        for (EastMoneyListData eastMoneyListData : resultFundList) {
            FutureTask<Long> longFutureTask = new FutureTask<>(new FundHistoryThreadQuick(eastMoneyListData.getFundCode()));
            exPoolList.add(longFutureTask);
            executorPool.submit(longFutureTask);
        }
        for (FutureTask<Long> longFutureTask : exPoolList) {
            log.info("down: size:" + longFutureTask.get() + "/" + total);
        }
        log.info("down: size:" + total);
    }

    class FundHistoryThreadQuick implements Callable<Long> {
        private String fundCode;

        public FundHistoryThreadQuick(String fundCode) {
            this.fundCode = fundCode;
        }

        @SneakyThrows
        @Override
        public Long call() {
            try {
                EastmoneyHistoryResult eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, 1, pageSize);
                List<EastMoneyHistoryLSJZConverter> historyLSJZConverters = eastmoneyFundHistory.getData().getLSJZList().stream().map(v -> {
                    return getHistoryLSJZConverter(v, fundCode);
                }).collect(Collectors.toList());

                fundStoreForest.save1(JSON.toJSONString(historyLSJZConverters));

                Integer page = (eastmoneyFundHistory.getTotalCount() / pageSize) + ((eastmoneyFundHistory.getTotalCount() % pageSize > 0) ? 1 : 0);
                if (page > 0) {
                    List<FutureTask<String>> exPoolList = new ArrayList<>();
                    for (int i = 2; i <= page; i++) {
                        FutureTask<String> longFutureTask = new FutureTask<String>(new TestFundHistoryForest.FundHistoryThreadQuickNext(fundCode, i));
                        exPoolList.add(longFutureTask);
                        executorPoolNext.submit(longFutureTask);
                    }
                    for (FutureTask<String> stringFutureTask : exPoolList) {
                        stringFutureTask.get();
                    }
                }
            } catch (Exception e) {
                log.error("Error:" + e.getMessage());
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : " + e.getMessage());
            }
            return result.addAndGet(1);
        }
    }

    class FundHistoryThreadQuickNext implements Callable<String>  {

        private String fundCode;
        private Integer pageIndex;

        public FundHistoryThreadQuickNext(String fundCode, Integer pageIndex) {
            this.fundCode = fundCode;
            this.pageIndex = pageIndex;
        }

        @SneakyThrows
        @Override
        public String call() {
            try {
                EastmoneyHistoryResult eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, pageIndex, pageSize);
                List<EastMoneyHistoryLSJZConverter> historyLSJZConverters = eastmoneyFundHistory.getData().getLSJZList().stream().map(v ->
                        getHistoryLSJZConverter(v, fundCode)
                ).collect(Collectors.toList());
                executeS(historyLSJZConverters, pageIndex);
            } catch (Exception e) {
                log.error("Error:" + e.getMessage());
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : page" + pageIndex + " : " + e.getMessage());
            }
            return fundCode;
        }
    }

    public void executeS(List<EastMoneyHistoryLSJZConverter> historyLSJZConverters,Integer pageSize){
        if (pageSize % 3 == 0){
            fundStoreForest.save1(JSON.toJSONString(historyLSJZConverters));
        }else if (pageSize % 3 == 1){
            fundStoreForest.save2(JSON.toJSONString(historyLSJZConverters));
        }else if (pageSize % 3 == 2){
            fundStoreForest.save3(JSON.toJSONString(historyLSJZConverters));
        }else {
            fundStoreForest.save3(JSON.toJSONString(historyLSJZConverters));
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
