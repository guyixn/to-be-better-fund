package com.fund.collect.liteflow.component;

import cn.hutool.core.util.StrUtil;
import com.fund.api.constant.IConstant;
import com.fund.collect.eastmoney.entity.overview.EastMoneyMarketOverviewData;
import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarketOverviewCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        DailySlot dailySlot = this.getSlot();
        Document marketOverviewDoc = Jsoup.connect("https://fund.eastmoney.com/company/default.html").get();
        Elements marketOverviewElement = marketOverviewDoc.body().select("div.outer_all > div.ttjj-grid-row > div.main-content.ttjj-grid-21 > div.third-block.jcommon-block > div.common-block-con > table > tbody");
        EastMoneyMarketOverviewData e = new EastMoneyMarketOverviewData();
        e.setTotalAmount(Double.parseDouble(marketOverviewElement.select("tr:nth-child(2) > td:nth-child(2)").text()));
        e.setTotalNum(Long.parseLong(marketOverviewElement.select("tr:nth-child(3) > td:nth-child(2)").text()));
        e.setStockAmount(Double.parseDouble(marketOverviewElement.select("tr:nth-child(2) > td:nth-child(3)").text()));
        e.setStockNum(Long.parseLong(marketOverviewElement.select("tr:nth-child(3) > td:nth-child(3)").text()));
        e.setMixedAmount(Double.parseDouble(marketOverviewElement.select("tr:nth-child(2) > td:nth-child(4)").text()));
        e.setMixedNum(Long.parseLong(marketOverviewElement.select("tr:nth-child(3) > td:nth-child(4)").text()));
        e.setBondsAmount(Double.parseDouble(marketOverviewElement.select("tr:nth-child(2) > td:nth-child(5)").text()));
        e.setBondsNum(Long.parseLong(marketOverviewElement.select("tr:nth-child(3) > td:nth-child(5)").text()));
        e.setIndexAmount(Double.parseDouble(marketOverviewElement.select("tr:nth-child(2) > td:nth-child(6)").text()));
        e.setIndexNum(Long.parseLong(marketOverviewElement.select("tr:nth-child(3) > td:nth-child(6)").text()));
        e.setQdiiAmount(Double.parseDouble(marketOverviewElement.select("tr:nth-child(2) > td:nth-child(7)").text()));
        e.setQdiiNum(Long.parseLong(marketOverviewElement.select("tr:nth-child(3) > td:nth-child(7)").text()));
        e.setCurrencyAmount(Double.parseDouble(marketOverviewElement.select("tr:nth-child(2) > td:nth-child(8)").text()));
        e.setCurrencyNum(Long.parseLong(marketOverviewElement.select("tr:nth-child(3) > td:nth-child(8)").text()));
        e.setEndDate(dailySlot.getMarketOverviewDate());
        String formatScaleLog = "基金管理规模(亿元) 全部:" + e.getTotalAmount() + StrUtil.C_COMMA + StrUtil.C_SPACE + "股票型:" + e.getStockAmount() + StrUtil.C_COMMA + StrUtil.C_SPACE + "混合型:" + e.getMixedAmount() + StrUtil.C_COMMA + StrUtil.C_SPACE + "债券型:" + e.getBondsAmount() + StrUtil.C_COMMA + StrUtil.C_SPACE + "指数型:" + e.getIndexAmount() + StrUtil.C_COMMA + StrUtil.C_SPACE + "QDII:" + e.getQdiiAmount() + StrUtil.C_COMMA + StrUtil.C_SPACE + "货币型:" + e.getCurrencyAmount() + "\n";
        String formatNumLog = "基金数量(只) 全部:" + e.getTotalNum() + StrUtil.C_COMMA + StrUtil.C_SPACE + "股票型:" + e.getStockNum() + StrUtil.C_COMMA + StrUtil.C_SPACE + "混合型:" + e.getMixedNum() + StrUtil.C_COMMA + StrUtil.C_SPACE + "债券型:" + e.getBondsNum() + StrUtil.C_COMMA + StrUtil.C_SPACE + "指数型:" + e.getIndexNum() + StrUtil.C_COMMA + StrUtil.C_SPACE + "QDII:" + e.getQdiiNum() + StrUtil.C_COMMA + StrUtil.C_SPACE + "货币型:" + e.getCurrencyNum();
        dailySlot.getStepLog().append(IConstant.DASH_WRAP);
        dailySlot.getStepLog().append(formatScaleLog + formatNumLog);
        log.info(formatScaleLog + formatNumLog);
    }
}
