package com.collect_fund.eastmoney;

import cn.hutool.core.date.DateUtil;
import com.api_fund.EastmoneyHistoryLSJZConverter;
import com.collect_fund.ToCollectFundApplication;
import com.collect_fund.eastmoney.entity.fund_history.EastmoneyHistoryLSJZ;
import com.collect_fund.eastmoney.entity.fund_history.EastmoneyHistoryResult;
import com.collect_fund.eastmoney.forest.FundHistoryForest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

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

    final static ExecutorService executorPool = new ThreadPoolExecutor(15, 15,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    CountDownLatch cd = null;
    CountDownLatch cdn = null;

    AtomicLong result = new AtomicLong(0);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String fundCodeIndex = "fund-code";

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * 测试获取所有的基金
     * @throws InterruptedException
     */
    @Test
    void getAllFundHistory() throws InterruptedException {
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(fundCodeIndex);
        Integer pageSize = 20;
        cd = new CountDownLatch(keys.size());
        Integer progress = 0;
        for (Object key : keys) {
            result.set(0);
            String fundCode = (String) key;
            EastmoneyHistoryResult eastmoneyFundHistory = null;
            try {
                eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, 1, pageSize);
                eastmoneyFundHistory.getData().getLSJZList().forEach(v -> {
                    kafkaTemplate.send(EastmoneyHistoryLSJZ.TOPIC, getHistoryLSJZConverter(v, fundCode));
                });
            } catch (Exception e) {
                log.error("Error f:" + e.getMessage());
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : page1 : " + e.getMessage());
            }
            try {
                Integer page = (eastmoneyFundHistory.getTotalCount() / pageSize) + ((eastmoneyFundHistory.getTotalCount() % pageSize > 0) ? 1 : 0);
                result.addAndGet(eastmoneyFundHistory.getData().getLSJZList().size());
                cdn = new CountDownLatch(page - 1);
                for (int i = 2; i <= page; i++) {
                    executorPool.execute(new TestFundHistoryForest.FundHistoryThread(fundCode, i, pageSize));
                }
            } catch (Exception e) {
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : pageC1 : " + e.getMessage());
            }
            cdn.await();
            cd.countDown();
            log.info(++progress + "/"+ keys.size() + ",fundCode: " + fundCode + ",result size:" + result);
        }
        cd.await();
    }

    @Test
    void getFundHistory() throws InterruptedException {
        String fundCode = "000001";
        Integer pageSize = 20;

        EastmoneyHistoryResult eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, 1, pageSize);
        Integer page = (eastmoneyFundHistory.getTotalCount() / pageSize) + ((eastmoneyFundHistory.getTotalCount() % pageSize > 0) ? 1 : 0);
        cd = new CountDownLatch(page-1);
        result.addAndGet(eastmoneyFundHistory.getData().getLSJZList().size());
        for (int i = 2; i <= page; i++) {
            executorPool.execute(new TestFundHistoryForest.FundHistoryThread(fundCode, i, pageSize));
        }
        cd.await();

        System.out.println("result size:" + result);
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

    class FundHistoryThread extends Thread {

        private String fundCode;
        private Integer pageIndex;
        private Integer pageSize;

        public FundHistoryThread(String fundCode, Integer pageIndex, Integer pageSize) {
            this.fundCode = fundCode;
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }

        @SneakyThrows
        @Override
        public void run() {
            try {
                EastmoneyHistoryResult eastmoneyFundHistory = fundHistoryForest.getFundHistory(fundCode, pageIndex, pageSize);

                result.addAndGet(eastmoneyFundHistory.getData().getLSJZList().size());
                eastmoneyFundHistory.getData().getLSJZList().forEach(v->{
                    v.setFundCode(fundCode);
                    kafkaTemplate.send(EastmoneyHistoryLSJZ.TOPIC, getHistoryLSJZConverter(v, fundCode));
                });
//                log.info("Thread Id[" + Thread.currentThread().getId() + "]-End,Page Index: " + pageIndex + ", Result Size[" + result + "]" );
            } catch (Exception e) {
                log.error("Error:" + e.getMessage());
                stringRedisTemplate.opsForList().leftPush("error", fundCode + " : page" + pageIndex + " : " + e.getMessage());
            }
            cdn.countDown();
        }
    }

}
