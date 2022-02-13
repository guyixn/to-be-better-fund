package com.collect_fund.eastmoney;

import com.alibaba.fastjson.JSON;
import com.collect_fund.ToCollectFundApplication;
import com.collect_fund.eastmoney.entity.fund_history.EastmoneyHistoryResult;
import com.collect_fund.eastmoney.forest.FundHistoryForest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: tbb
 * @Date: 2022/2/13 23:14
 * @Description: 测试基金历史净值
 */
@SpringBootTest(classes = ToCollectFundApplication.class)
public class TestFundHistoryForest {
    @Autowired
    private FundHistoryForest fundHistoryForest;

    @Test
    void getFundAllRank() {
        EastmoneyHistoryResult eastmoneyFundHistory = fundHistoryForest.getFundHistory("161130", "1", "20");
        eastmoneyFundHistory.getData().getLSJZList().forEach(v -> {
            System.out.println(JSON.toJSONString(v));
        });
    }
}
