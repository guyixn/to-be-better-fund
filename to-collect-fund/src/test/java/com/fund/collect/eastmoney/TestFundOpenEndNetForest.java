package com.fund.collect.eastmoney;

import com.fund.collect.ToCollectFundApplication;
import com.fund.collect.eastmoney.entity.openNet.EastmoneyOpenNetResult;
import com.fund.collect.eastmoney.forest.FundOpenEndNetForest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: tbb
 * @Date: 2022/2/13 0:33
 * @Description: 测试开放式基金净值
 */
@SpringBootTest(classes = ToCollectFundApplication.class)
public class TestFundOpenEndNetForest {
    @Autowired
    private FundOpenEndNetForest fundOpenEndNetForest;

    @Test
    void getFundAllRank(){
        EastmoneyOpenNetResult openEndNetListResult = fundOpenEndNetForest.getFundOpenEndNet("1,200");
        openEndNetListResult.getDatas().forEach(v->{
            System.out.println(v);
        });
    }
}
