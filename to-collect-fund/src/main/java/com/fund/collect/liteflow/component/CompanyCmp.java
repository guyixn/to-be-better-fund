package com.fund.collect.liteflow.component;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fund.api.constant.IConstant;
import com.fund.collect.eastmoney.entity.company.EastmoneyCompanyData;
import com.fund.collect.eastmoney.entity.company.EastmoneyCompanyResult;
import com.fund.collect.eastmoney.forest.FundCompanyForest;
import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyCmp extends NodeComponent {

    @Autowired
    private FundCompanyForest companyClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void process() throws Exception {
        DailySlot dailySlot = this.getContextBean(DailySlot.class);
        EastmoneyCompanyResult eastmoneyCompanyResult = companyClient.getCompanyList();
        List<EastmoneyCompanyData> es = eastmoneyCompanyResult.getData().stream().map(v -> {
            EastmoneyCompanyData e = new EastmoneyCompanyData();
            e.setCompanyAccount(v.get(0));
            e.setCompanyName(v.get(1));
            e.setSetDate(DateUtil.parseDate(v.get(2)));
            e.setFundSum(v.get(3));
            e.setManagerName(v.get(4));
            e.setCompanyCode(v.get(5));
            e.setScale(StrUtil.isBlank(v.get(7)) ? 0d : Double.valueOf(v.get(7)));
            e.setTianXiangLevel(v.get(8));
            e.setCompanyShortName(v.get(9));
            e.setEndDate(StrUtil.isBlank(v.get(11)) ? null : DateUtil.parseDate(v.get(11)));
            return e;
        }).collect(Collectors.toList());
        dailySlot.getStepLog().append(IConstant.DASH_WRAP).append("基金公司:"+es.size());
//        dailySlot.getStepLog().append(IConstant.DASH_LEFT);
    }
}
