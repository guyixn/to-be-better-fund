package com.fund.collect.eastmoney;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @Author: tbb
 * @Date: 2022/2/15 22:35
 * @Description:
 */
@Slf4j
public class ToTestParse {
    public static void main(String[] args) {
        String t = "";
        Document doc = Jsoup.parseBodyFragment(t);
        Element body = doc.body();
        Elements resultLinks = body.select("table > tbody > tr");
        for (Element headline : resultLinks) {
            StringBuilder sb = new StringBuilder();
            sb.append(headline.select("td:nth-child(3)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(4)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(6)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(7)").text()).append(" || ");

            sb.append(headline.select("td:nth-child(8)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(9)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(10)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(11)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(12)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(13)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(14)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(15)").text()).append(" || ");

            log.info(sb.toString());
        }

    }
}
