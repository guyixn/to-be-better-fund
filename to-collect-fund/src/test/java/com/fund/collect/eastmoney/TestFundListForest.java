package com.fund.collect.eastmoney;

import com.alibaba.fastjson.JSON;
import com.fund.collect.ToCollectFundApplication;
import com.fund.collect.eastmoney.entity.list.EastMoneyListData;
import com.fund.collect.eastmoney.entity.list.EastMoneyListResult;
import com.fund.collect.eastmoney.forest.FundListForest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = ToCollectFundApplication.class)
@Slf4j
public class TestFundListForest {
    @Autowired
    FundListForest fundListForest;

    @Test
    public void testFundList(){
        EastMoneyListResult fundList = fundListForest.getFundList();
        List<EastMoneyListData> list = fundList.getData().stream().map(v -> {
            EastMoneyListData e = new EastMoneyListData();
            e.setFundCode(v.get(0));
            e.setFundName(v.get(2));
            e.setFundType(v.get(3));
            return e;
        }).collect(Collectors.toList());
        list.forEach(v->{
            System.out.println(JSON.toJSONString(v));
        });
    }

}
