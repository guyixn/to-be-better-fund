package com.fund.collect.liteflow.component;

import com.fund.api.constant.IConstant;
import com.fund.collect.eastmoney.entity.fix.EastmoneyFixedInvestResult;
import com.fund.collect.eastmoney.forest.FundFixedInvestForest;
import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FundFixCmp  extends NodeComponent {

    @Autowired
    FundFixedInvestForest fundFixedInvestForest;

    @Override
    public void process() throws Exception {
        DailySlot dailySlot = getSlot();
        EastmoneyFixedInvestResult result = fundFixedInvestForest.getFundFixedInvest(1,100);
        Document doc = Jsoup.parseBodyFragment(result.getData());
        Element body = doc.body();
        Elements resultLinks = body.select("table > tbody > tr");
//        for (Element headline : resultLinks) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(headline.select("td:nth-child(3)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(4)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(6)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(7)").text()).append(" - ");
//
//            sb.append(headline.select("td:nth-child(8)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(9)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(10)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(11)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(12)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(13)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(14)").text()).append(" - ");
//            sb.append(headline.select("td:nth-child(15)").text()).append(" - ");
//            log.info(sb.toString());
//        }
        dailySlot.getStepLog().append(IConstant.DASH_WRAP).append("基金定投:" + resultLinks.size()).append(IConstant.RIGHT_SIGN).append("X");
    }
}
