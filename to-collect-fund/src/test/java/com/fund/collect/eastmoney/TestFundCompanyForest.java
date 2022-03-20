package com.fund.collect.eastmoney;

import com.alibaba.fastjson.JSON;
import com.fund.collect.ToCollectFundApplication;
import com.fund.collect.eastmoney.entity.company.EastmoneyCompanyResult;
import com.fund.collect.eastmoney.forest.FundCompanyForest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ToCollectFundApplication.class)
public class TestFundCompanyForest {

    @Autowired
    private FundCompanyForest companyClient;

    @Test
    void getCompanyList(){
        EastmoneyCompanyResult eastmoneyCompanyResult = companyClient.getCompanyList();
//        System.out.println(JSON.toJSONString(eastmoneyCompanyListResult));

        eastmoneyCompanyResult.getData().forEach(v->{
            System.out.println(JSON.toJSONString(v));
        });
    }
}
