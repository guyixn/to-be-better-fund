package com.fund.store;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fund.store.eastmoney.entity.TFundCompany;
import com.fund.store.eastmoney.service.ITFundCompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ToStoreFundApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    ITFundCompanyService itFundCompanyService;

    @Test
    void getFundCompanyList(){
        List<TFundCompany> list =  itFundCompanyService.list();
        list.forEach(v->{
            System.out.println(JSON.toJSONString(v, SerializerFeature.WriteMapNullValue));
        });
    }
}
