package com.collect_fund.eastmoney;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.collect_fund.ToCollectFundApplication;
import com.collect_fund.eastmoney.entity.all_rank.EastmoneyAllRankData;
import com.collect_fund.eastmoney.entity.all_rank.EastmoneyAllRankResult;
import com.collect_fund.eastmoney.forest.FundAllRankForest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
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

    List<String> result = Collections.synchronizedList(new ArrayList<>(10_000));

    final static ExecutorService executorPool = new ThreadPoolExecutor(15, 15,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    CountDownLatch cd = null;

    @Test
    void getFundAllRank() throws InterruptedException {
        EastmoneyAllRankResult allRankListResult = fundAllRankForest.getFundAllRank("2022-02-12", "2022-02-12", 1, 50);
        cd = new CountDownLatch(allRankListResult.getAllPages()-1);
        List<EastmoneyAllRankData> pojoResultList = allRankListResult.getData().stream().map(v -> {
            EastmoneyAllRankData allRankListData = new EastmoneyAllRankData();
            String[] r = v.split(",");
            allRankListData.setFundCode(r[0]);
            allRankListData.setFundShortName(r[1]);
            allRankListData.setFundShortCode(r[2]);
            allRankListData.setFundDate(DateUtil.parseDate(r[3]));
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
            return allRankListData;
        }).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(pojoResultList,true));
        allRankListResult.getData().forEach(v -> {
            result.add(v);
        });

        for (int i = 2; i <= allRankListResult.getAllPages(); i++) {
            executorPool.execute(new FundAllRankThread("2022-02-12", "2022-02-12", i, 50));
        }
        cd.await();

        System.out.println("result size:" + result.size());
        result.forEach(v->{
            log.info(v);
        });
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
                EastmoneyAllRankResult allRankListResult = fundAllRankForest.getFundAllRank(startDate, endDate, pageIndex, pageSize);
                allRankListResult.getData().forEach(v -> {
                    result.add(v);
                });
                log.info("Thread Id[" + Thread.currentThread().getId() + "]-End,Page Index: " + pageIndex + ", Result Size[" + result.size() + "]");
            } catch (Exception e) {
                log.error("Error:" + e.getMessage());
            }
            cd.countDown();
        }
    }
}
