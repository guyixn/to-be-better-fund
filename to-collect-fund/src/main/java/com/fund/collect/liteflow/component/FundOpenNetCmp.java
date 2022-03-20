package com.fund.collect.liteflow.component;

import com.fund.api.constant.IConstant;
import com.fund.collect.eastmoney.entity.openNet.EastmoneyOpenNetResult;
import com.fund.collect.eastmoney.forest.FundOpenEndNetForest;
import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FundOpenNetCmp extends NodeComponent {

    @Autowired
    private FundOpenEndNetForest fundOpenEndNetForest;

    @Override
    public void process() throws Exception {
        DailySlot dailySlot = getSlot();
        EastmoneyOpenNetResult openEndNetListResult = fundOpenEndNetForest.getFundOpenEndNet("1,200");
        dailySlot.getStepLog().append(IConstant.DASH_WRAP)
                .append("开放式基金净值:" + openEndNetListResult.getDatas().size())
                .append(IConstant.RIGHT_SIGN)
                .append("X");
    }
}
