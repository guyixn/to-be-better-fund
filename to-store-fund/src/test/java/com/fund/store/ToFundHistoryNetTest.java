package com.fund.store;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.store.eastmoney.entity.TFundHistoryNet;
import com.fund.store.eastmoney.service.ITFundHistoryNetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@SpringBootTest(classes = ToStoreFundApplication.class)
@Slf4j
public class ToFundHistoryNetTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    CountDownLatch cd = null;

    AtomicLong result = new AtomicLong(0);
    int total = 0;

    @Autowired
    ITFundHistoryNetService itFundHistoryNetService;

    private String fundCodeIndex = "fund-code";

    @Test
    public void store() throws InterruptedException {
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(fundCodeIndex);
        cd = new CountDownLatch(keys.size());
        total = keys.size();
        result.set(0);
//        itFundHistoryNetService
        for (Object key : keys) {
//            long o = result.addAndGet(-1);
//            if (o < 0) {
//                break;
//            }
            try {
                String fundCode = (String) key;
                List<Object> values = stringRedisTemplate.opsForHash().values(fundCode);
                for (Object value : values) {
                    List<TFundHistoryNet> collect = JSON.parseArray((String) value, EastMoneyHistoryLSJZConverter.class).stream().map(v -> {
                        TFundHistoryNet t = new TFundHistoryNet();
                        t.setFundCode(v.getFundCode());
                        t.setFundDate(DateUtil.parseDate(v.getFundDate()));
                        if (!StrUtil.isBlank(v.getUnitNet()) && !"--".equals(v.getUnitNet())) {
                            t.setUnitNet(Double.parseDouble(v.getUnitNet()));
                        }
                        if (!StrUtil.isBlank(v.getSumNet()) && !"--".equals(v.getSumNet())) {
                            t.setSumNet(Double.parseDouble(v.getSumNet()));
                        }
                        if (!StrUtil.isBlank(v.getDayGrowRate()) && !"--".equals(v.getDayGrowRate())) {
                            t.setDayGrowRate(Double.parseDouble(v.getDayGrowRate()));
                        }
                        t.setBuyStatus(v.getBuyStatus());
                        t.setRedStatus(v.getRedStatus());
                        t.setDivide(v.getDivide());
                        t.setCreateDate(DateUtil.parseDate(v.getCreateDate()));
                        return t;
                    }).collect(Collectors.toList());
                    itFundHistoryNetService.saveBatch(collect);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cd.countDown();
            result.addAndGet(1);
            log.info(key + ",:" + result.get() + "/" + total);
        }
        cd.await();
    }
}
