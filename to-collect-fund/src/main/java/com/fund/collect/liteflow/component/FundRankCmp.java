package com.fund.collect.liteflow.component;

import cn.hutool.core.date.DateUtil;
import com.fund.api.constant.IConstant;
import com.fund.collect.eastmoney.entity.rank.EastMoneyRankResult;
import com.fund.collect.eastmoney.forest.FundRankForest;
import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FundRankCmp extends NodeComponent {

    @Autowired
    private FundRankForest fundAllRankForest;

    @Override
    public void process() throws Exception {
        DailySlot dailySlot = getSlot();
        EastMoneyRankResult allRankListResult = fundAllRankForest.getFundAllRank(DateUtil.formatDate(dailySlot.getOpenNetDate()), DateUtil.formatDate(dailySlot.getOpenNetDate()), 1, 50);
        dailySlot.getStepLog().append(IConstant.DASH_WRAP).append("基金排行:" + allRankListResult.getData().size()).append(IConstant.RIGHT_SIGN).append("X");
    }
}
