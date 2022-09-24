package com.fund.collect.xxl_job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fund.api.converter.EastMoneyHistoryLSJZConverter;
import com.fund.collect.eastmoney.entity.rank.EastMoneyRankData;
import com.fund.collect.eastmoney.entity.rank.EastMoneyRankResult;
import com.fund.collect.eastmoney.forest.FundRankForest;
import com.fund.entity.result.Result;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EMFundRankXxlJob {
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {

        for (int i = 0; i < 5; i++) {
            log.info("beat at:" + i);
            TimeUnit.SECONDS.sleep(1);
        }
        // default success
    }

    @Autowired
    private FundRankForest fundAllRankForest;
    @Autowired
    RestTemplate restTemplate;

    AtomicInteger result = new AtomicInteger(0);

    final static ExecutorService executorPool = new ThreadPoolExecutor(15, 15,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    CountDownLatch cd = null;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String initDate = DateUtil.yesterday().toDateStr();

    private String fundCodeIndex = "fund-code";

    @XxlJob("getFundAllRankJob")
    void getFundAllRank() throws InterruptedException {
        //
        // if (!initDate.equals("0")){
        //     System.out.println(initDate);
        //     return;
        // }

        EastMoneyRankResult allRankListResult = fundAllRankForest.getFundAllRank(initDate, initDate, 1, 50);
        cd = new CountDownLatch(allRankListResult.getAllPages() - 1);
        List<EastMoneyRankData> pojoResultList = allRankListResult.getData().stream().map(v -> {
            EastMoneyRankData allRankListData = new EastMoneyRankData();
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
        for (EastMoneyRankData v : pojoResultList) {
            if (Objects.isNull(v.getFundDate())) {
                continue;
            }
            EastMoneyHistoryLSJZConverter historyLSJZConverter = getHistoryLSJZConverter(v);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EastMoneyHistoryLSJZConverter> request = new HttpEntity<>(historyLSJZConverter, headers);
            restTemplate.postForEntity("http://to-store-fund/eastmoney/fund_history/save", request, Result.class);
            log.info(JSON.toJSONString(pojoResultList));
        }
        // stringRedisTemplate.executePipelined(
        //         new RedisCallback<String>() {
        //             @Override
        //             public String doInRedis(RedisConnection connection) {
        //                 pojoResultList.forEach(v -> {
        //                     connection.hashCommands().hSet(fundCodeIndex.getBytes(), v.getFundCode().getBytes(), JSON.toJSONString(v, SerializerFeature.WriteMapNullValue).getBytes());
        //                 });
        //                 return null;
        //             }
        //         }
        // );

        // pojoResultList.forEach(v -> {
        //     kafkaTemplate.send(EastmoneyAllRankData.TOPIC, v);
        // });
        result.addAndGet(pojoResultList.size());

        for (int i = 2; i <= allRankListResult.getAllPages(); i++) {
            executorPool.execute(new FundAllRankThread(initDate, initDate, i, 50));
        }
        cd.await();

        System.out.println("result size:" + result);
    }

    public static Double toDouble(String value) {
        return StrUtil.isEmpty(value) ? 0.0d : Double.parseDouble(value.replace("%", ""));
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
                EastMoneyRankResult allRankListResult = fundAllRankForest.getFundAllRank(startDate, endDate, pageIndex, pageSize);

                List<EastMoneyRankData> pojoResultList = allRankListResult.getData().stream().map(v -> {
                    EastMoneyRankData allRankListData = new EastMoneyRankData();
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
                for (EastMoneyRankData v : pojoResultList) {
                    if (Objects.isNull(v.getFundDate())) {
                        continue;
                    }
                    EastMoneyHistoryLSJZConverter historyLSJZConverter = getHistoryLSJZConverter(v);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<EastMoneyHistoryLSJZConverter> request = new HttpEntity<>(historyLSJZConverter, headers);
                    restTemplate.postForEntity("http://to-store-fund/eastmoney/fund_history/save", request, Result.class);
                    log.info(JSON.toJSONString(pojoResultList));
                }

                // stringRedisTemplate.executePipelined(
                //         new RedisCallback<String>() {
                //             @Override
                //             public String doInRedis(RedisConnection connection) {
                //                 pojoResultList.forEach(v -> {
                //                     connection.hashCommands().hSet(fundCodeIndex.getBytes(), v.getFundCode().getBytes(), JSON.toJSONString(v, SerializerFeature.WriteMapNullValue).getBytes());
                //                 });
                //                 return null;
                //             }
                //         }
                // );
//
//                pojoResultList.forEach(v -> {
//                    kafkaTemplate.send("fund-all-rank", v);
//                });

                result.addAndGet(allRankListResult.getData().size());
                log.info("Thread Id[" + Thread.currentThread().getId() + "]-End,Page Index: " + pageIndex + ", Result Size[" + result + "]");
            } catch (Exception e) {
                log.error("Error:" + e.getMessage());
            }
            cd.countDown();
        }
    }

    public EastMoneyHistoryLSJZConverter getHistoryLSJZConverter(EastMoneyRankData e) {
        EastMoneyHistoryLSJZConverter c = new EastMoneyHistoryLSJZConverter();
        c.setFundCode(e.getFundCode());
        c.setFundShortName(e.getFundShortName());
        c.setFundDate(dateToLocalDate(e.getFundDate()));
        c.setUnitNet(String.valueOf(e.getUnitNet()));
        c.setSumNet(String.valueOf(e.getSumNet()));
        c.setDayGrowRate(String.valueOf(e.getDayGrowRate()));
        c.setBuyStatus(e.getBuyStatus());
        c.setRedStatus(e.getBuyStatus());
        c.setDivide("");
        c.setCreateDateTime(LocalDateTime.now());
        return c;
    }

    /**
     * 将 Date 转为 LocalDate
     *
     * @param date
     * @return java.time.LocalDate;
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
