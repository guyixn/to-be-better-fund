package com.fund.collect.liteflow.component;

import com.alibaba.fastjson.JSON;
import com.fund.api.constant.IConstant;
import com.fund.collect.eastmoney.entity.list.EastMoneyListData;
import com.fund.collect.eastmoney.entity.list.EastMoneyListResult;
import com.fund.collect.eastmoney.forest.FundListForest;
import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FundListCmp extends NodeComponent {

    @Autowired
    FundListForest fundListForest;

    @Override
    public void process() throws Exception {
        DailySlot dailySlot = getSlot();
        EastMoneyListResult fundList = fundListForest.getFundList();
        List<EastMoneyListData> cFundList = fundList.getData().stream().map(v -> {
            EastMoneyListData e = new EastMoneyListData();
            e.setFundCode(v.get(0));
            e.setFundName(v.get(2));
            e.setFundType(v.get(3));
            return e;
        }).collect(Collectors.toList());
        dailySlot.getStepLog().append(IConstant.DASH_WRAP).append("基金列表:"+cFundList.size()).append(IConstant.RIGHT_SIGN).append("X");
    }
}
