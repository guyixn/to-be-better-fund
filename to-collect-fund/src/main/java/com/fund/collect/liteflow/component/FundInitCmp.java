package com.fund.collect.liteflow.component;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fund.api.constant.IConstant;
import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FundInitCmp extends NodeComponent {


    @Override
    public void process() throws Exception {
        DailySlot dailySlot = this.getSlot();
        //设置开始时间
        dailySlot.setStartDate(DateUtil.date());
        //基金市场概况日期
        Document marketOverviewDoc = Jsoup.connect("https://fund.eastmoney.com/company/default.html").get();
        Elements marketOverviewElement = marketOverviewDoc.body().select("div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-sub-title.clearfix > div.pull-right > p");
        dailySlot.setMarketOverviewDate(DateUtil.parseDate(marketOverviewElement.text().replace("截止日期：","")));

        //获取开放式基金净值数据日期
        Document openNetDoc = Jsoup.connect("https://fund.eastmoney.com/fund.html").get();
        Elements openNetElement = openNetDoc.body().select("div:nth-child(9) > div.filter > div.tabs > ul:nth-child(3) > li:nth-child(1) > span");
        dailySlot.setOpenNetDate(DateUtil.parseDate(openNetElement.text()));
        String formatLog = "开始日期:" + DateUtil.format(dailySlot.getStartDate(), DatePattern.NORM_DATETIME_FORMAT) + ",基金市场概况日期:" + DateUtil.format(dailySlot.getMarketOverviewDate(),DatePattern.NORM_DATE_FORMAT) + ",开放式基金净值数据日期:" + openNetElement.text();
        dailySlot.getStepLog().append(IConstant.DASH_RIGHT).append(formatLog);
        log.info(formatLog);

    }

}
