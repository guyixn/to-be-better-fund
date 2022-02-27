package com.collect_fund.eastmoney;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.collect_fund.ToCollectFundApplication;
import com.collect_fund.eastmoney.entity.all_rank.EastmoneyAllRankData;
import com.collect_fund.eastmoney.entity.all_rank.EastmoneyAllRankResult;
import com.collect_fund.eastmoney.forest.FundAllRankForest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: tbb
 * @Date: 2022/2/12 22:32
 * @Description: 测试allRank
 */
@SpringBootTest(classes = ToCollectFundApplication.class)
@Slf4j
public class TestFundAllRankForest {
    @Autowired
    private FundAllRankForest fundAllRankForest;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    AtomicInteger result = new AtomicInteger(0);

    final static ExecutorService executorPool = new ThreadPoolExecutor(15, 15,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    CountDownLatch cd = null;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String initDate = DateUtil.today();

    private String fundCodeIndex = "fund-code";

    @Test
    void getFundAllRank() throws InterruptedException {
        EastmoneyAllRankResult allRankListResult = fundAllRankForest.getFundAllRank(initDate, initDate, 1, 50);
        cd = new CountDownLatch(allRankListResult.getAllPages() - 1);
        List<EastmoneyAllRankData> pojoResultList = allRankListResult.getData().stream().map(v -> {
            EastmoneyAllRankData allRankListData = new EastmoneyAllRankData();
            String[] r = v.split(",", -1);
            allRankListData.setFundCode(r[0]);
            allRankListData.setFundShortName(r[1]);
            allRankListData.setFundShortCode(r[2]);
            allRankListData.setFundDate(StrUtil.NULL.equals(r[3]) || StrUtil.isBlank(r[3]) ? null : DateUtil.parseDate(r[3]));
            allRankListData.setUnitNet(toDouble(r[4]));
            allRankListData.setSumNet(toDouble(r[5]));
            allRankListData.setDayGrowRate(toDouble(r[6]));
            allRankListData.setLastWeek(toDouble(r[7]));
            allRankListData.setLast1Month(toDouble(r[8]));
            allRankListData.setLast3Month(toDouble(r[9]));
            allRankListData.setLast6Month(toDouble(r[10]));
            allRankListData.setLast1Year(toDouble(r[11]));
            allRankListData.setLast2Year(toDouble(r[12]));
            allRankListData.setLast3Year(toDouble(r[13]));
            allRankListData.setThisYear(toDouble(r[14]));
            allRankListData.setSinceEstablish(toDouble(r[15]));
            allRankListData.setFee(toDouble(r[20]));
            allRankListData.setBuyStatus(r[17]);
            allRankListData.setCreateDate(new Date());
            return allRankListData;
        }).collect(Collectors.toList());

        stringRedisTemplate.executePipelined(
                new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection connection) {
                        pojoResultList.forEach(v -> {
                            connection.hashCommands().hSet(fundCodeIndex.getBytes(), v.getFundCode().getBytes(), JSON.toJSONString(v, SerializerFeature.WriteMapNullValue).getBytes());
                        });
                        return null;
                    }
                }
        );

        pojoResultList.forEach(v -> {
            kafkaTemplate.send(EastmoneyAllRankData.TOPIC, v);
        });
        result.addAndGet(allRankListResult.getData().size());

        for (int i = 2; i <= allRankListResult.getAllPages(); i++) {
            executorPool.execute(new FundAllRankThread(initDate, initDate, i, 50));
        }
        cd.await();

        System.out.println("result size:" + result);
    }

    public static Double toDouble(String value) {
        return StrUtil.isEmpty(value) ? 0.0d : Double.parseDouble(value.replace("%", ""));
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.parseDate(null));
    }

    class FundAllRankThread extends Thread {
        private String startDate;
        private String endDate;
        private Integer pageIndex;
        private Integer pageSize;

        public FundAllRankThread(String startDate, String endDate, Integer pageIndex, Integer pageSize) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }

        @SneakyThrows
        @Override
        public void run() {
            try {
                EastmoneyAllRankResult allRankListResult = fundAllRankForest.getFundAllRank(startDate, endDate, pageIndex, pageSize);

                List<EastmoneyAllRankData> pojoResultList = allRankListResult.getData().stream().map(v -> {
                    EastmoneyAllRankData allRankListData = new EastmoneyAllRankData();
                    String[] r = v.split(",", -1);
                    allRankListData.setFundCode(r[0]);
                    allRankListData.setFundShortName(r[1]);
                    allRankListData.setFundShortCode(r[2]);
                    allRankListData.setFundDate(StrUtil.NULL.equals(r[3]) || StrUtil.isBlank(r[3]) ? null : DateUtil.parseDate(r[3]));
                    allRankListData.setUnitNet(toDouble(r[4]));
                    allRankListData.setSumNet(toDouble(r[5]));
                    allRankListData.setDayGrowRate(toDouble(r[6]));
                    allRankListData.setLastWeek(toDouble(r[7]));
                    allRankListData.setLast1Month(toDouble(r[8]));
                    allRankListData.setLast3Month(toDouble(r[9]));
                    allRankListData.setLast6Month(toDouble(r[10]));
                    allRankListData.setLast1Year(toDouble(r[11]));
                    allRankListData.setLast2Year(toDouble(r[12]));
                    allRankListData.setLast3Year(toDouble(r[13]));
                    allRankListData.setThisYear(toDouble(r[14]));
                    allRankListData.setSinceEstablish(toDouble(r[15]));
                    allRankListData.setFee(toDouble(r[20]));
                    allRankListData.setBuyStatus(r[17]);
                    allRankListData.setCreateDate(new Date());
                    return allRankListData;
                }).collect(Collectors.toList());

                stringRedisTemplate.executePipelined(
                        new RedisCallback<String>() {
                            @Override
                            public String doInRedis(RedisConnection connection) {
                                pojoResultList.forEach(v -> {
                                    connection.hashCommands().hSet(fundCodeIndex.getBytes(), v.getFundCode().getBytes(), JSON.toJSONString(v, SerializerFeature.WriteMapNullValue).getBytes());
                                });
                                return null;
                            }
                        }
                );

                pojoResultList.forEach(v -> {
                    kafkaTemplate.send("fund-all-rank", v);
                });

                result.addAndGet(allRankListResult.getData().size());
                log.info("Thread Id[" + Thread.currentThread().getId() + "]-End,Page Index: " + pageIndex + ", Result Size[" + result + "]" );
            } catch (Exception e) {
                log.error("Error:" + e.getMessage());
            }
            cd.countDown();
        }
    }
}
