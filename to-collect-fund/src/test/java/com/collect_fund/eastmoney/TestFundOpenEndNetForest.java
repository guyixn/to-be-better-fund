package com.collect_fund.eastmoney;

import com.collect_fund.ToCollectFundApplication;
import com.collect_fund.eastmoney.entity.open_end_net.EastmoneyOpenEndNetListResult;
import com.collect_fund.eastmoney.forest.FundOpenEndNetForest;
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
        EastmoneyOpenEndNetListResult openEndNetListResult = fundOpenEndNetForest.getFundOpenEndNet("1,200");
        openEndNetListResult.getDatas().forEach(v->{
            System.out.println(v);
        });
    }
}
