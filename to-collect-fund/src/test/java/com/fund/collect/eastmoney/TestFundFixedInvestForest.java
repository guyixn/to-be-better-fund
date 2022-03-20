package com.fund.collect.eastmoney;

import com.fund.collect.ToCollectFundApplication;
import com.fund.collect.eastmoney.entity.fix.EastmoneyFixedInvestResult;
import com.fund.collect.eastmoney.forest.FundFixedInvestForest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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
    public void testOpenEnd() throws IOException {

        Document doc = Jsoup.connect("http://fund.eastmoney.com/fund.html").get();
        log.info(""+doc.title());
        Element body = doc.body();
        Elements select = body.select("div:nth-child(9) > div.filter > div.tabs > ul:nth-child(3) > li:nth-child(1) > span");
        log.info(select.text());
    }


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
