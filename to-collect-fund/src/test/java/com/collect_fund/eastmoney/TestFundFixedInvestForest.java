package com.collect_fund.eastmoney;

import com.collect_fund.ToCollectFundApplication;
import com.collect_fund.eastmoney.entity.fix_invest.EastmoneyFixedInvestResult;
import com.collect_fund.eastmoney.forest.FundFixedInvestForest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: tbb
 * @Date: 2022/2/15 23:21
 * @Description: 测试基金定投
 */
@SpringBootTest(classes = ToCollectFundApplication.class)
@Slf4j
public class TestFundFixedInvestForest {

    @Autowired
    FundFixedInvestForest fundFixedInvestForest;

    @Test
    public void getFundFixedInvest(){
        EastmoneyFixedInvestResult result = fundFixedInvestForest.getFundFixedInvest(1,100);
        Document doc = Jsoup.parseBodyFragment(result.getData());
        Element body = doc.body();
        Elements resultLinks = body.select("table > tbody > tr");
        for (Element headline : resultLinks) {
            StringBuilder sb = new StringBuilder();
            sb.append(headline.select("td:nth-child(3)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(4)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(6)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(7)").text()).append(" - ");

            sb.append(headline.select("td:nth-child(8)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(9)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(10)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(11)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(12)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(13)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(14)").text()).append(" - ");
            sb.append(headline.select("td:nth-child(15)").text()).append(" - ");
            log.info(sb.toString());
        }

    }
}
