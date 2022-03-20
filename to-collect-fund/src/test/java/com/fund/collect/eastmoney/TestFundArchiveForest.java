package com.fund.collect.eastmoney;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fund.collect.ToCollectFundApplication;
import com.fund.collect.eastmoney.entity.archive.EastMoneyFundArchiveData;
import com.fund.collect.eastmoney.entity.archive.EastMoneyFundGeneralData;
import com.fund.collect.eastmoney.forest.FundArchiveForest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = ToCollectFundApplication.class)
@Slf4j
public class TestFundArchiveForest {

    @Autowired
    FundArchiveForest fundArchiveForest;

    @Test
    public void getFundArchive() {
        String fundArchive = fundArchiveForest.getFundArchive("000001");
        Document archiveDoc = Jsoup.parseBodyFragment(fundArchive);
        Element archiveBody = archiveDoc.body();
        Elements eleArchivebody = archiveBody.select("#bodydiv > div:nth-child(12) > div.r_cont.right > div.detail > div.txt_cont > div > div:nth-child(1) > table > tbody");
        String fundName = eleArchivebody.select("tr:nth-child(1) > td:nth-child(2)").text();
        String fundShortName = eleArchivebody.select("tr:nth-child(1) > td:nth-child(4)").text();
        String fundSlot = eleArchivebody.select("tr:nth-child(2) > td:nth-child(2)").text();
        String fundType = eleArchivebody.select("tr:nth-child(2) > td:nth-child(4)").text();
        String issueDate = eleArchivebody.select("tr:nth-child(3) > td:nth-child(2)").text();
        String establishDate = eleArchivebody.select("tr:nth-child(3) > td:nth-child(4)").text();
        String assetScale = eleArchivebody.select("tr:nth-child(4) > td:nth-child(2)").text();
        String shareScale = eleArchivebody.select("tr:nth-child(4) > td:nth-child(4)").text();
        String manager = eleArchivebody.select("tr:nth-child(5) > td:nth-child(2)").text();
        String trustee = eleArchivebody.select("tr:nth-child(5) > td:nth-child(4)").text();
        String managerMan = eleArchivebody.select("tr:nth-child(6) > td:nth-child(2)").text();
        String div = eleArchivebody.select("tr:nth-child(6) > td:nth-child(4)").text();
        String managerRate = eleArchivebody.select("tr:nth-child(7) > td:nth-child(2)").text();
        String escrowRate = eleArchivebody.select("tr:nth-child(7) > td:nth-child(4)").text();
        String saleServiceRate = eleArchivebody.select("tr:nth-child(8) > td:nth-child(2)").text();
        String maxSubRate = eleArchivebody.select("tr:nth-child(8) > td:nth-child(4)").text();
        String maxBuyRate = eleArchivebody.select("tr:nth-child(9) > td:nth-child(2)").text();
        String maxRedRate = eleArchivebody.select("tr:nth-child(9) > td:nth-child(4)").text();
        String achievementBenchMark = eleArchivebody.select("tr:nth-child(10) > td:nth-child(2)").text();
        String trackingTarget = eleArchivebody.select("tr:nth-child(10) > td:nth-child(4)").text();
        EastMoneyFundGeneralData eg = new EastMoneyFundGeneralData();
        eg.setFundCode("000001");
        eg.setFundName(fundName);
        eg.setFundShortName(fundShortName);
        eg.setFundSlot(fundSlot);
        eg.setFundType(fundType);
        eg.setIssueDate(issueDate);
        eg.setEstablishDate(establishDate);
        eg.setManager(manager);
        eg.setTrustee(trustee);
        EastMoneyFundArchiveData ea = new EastMoneyFundArchiveData();
        ea.setFundCode("000001");
        ea.setAssetScale(assetScale);
        ea.setShareScale(shareScale);
        ea.setManagerMan(managerMan);
        ea.setDiv(div);
        ea.setManagerRate(managerRate);
        ea.setEscrowRate(escrowRate);
        ea.setSaleServiceRate(saleServiceRate);
        ea.setMaxSubRate(maxSubRate);
        ea.setMaxBuyRate(maxBuyRate);
        ea.setMaxRedRate(maxRedRate);
        ea.setAchievementBenchMark(achievementBenchMark);
        ea.setTrackingTarget(trackingTarget);
        System.out.println(JSON.toJSONString(eg, SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(ea, SerializerFeature.PrettyFormat));
        String fundBuyInfo = fundArchiveForest.getFundBuyInfo("000001");
        Document fundBuyDoc = Jsoup.parseBodyFragment(fundBuyInfo);
        Element fundBuyBody = fundBuyDoc.body();
        Elements eleBuyBody = fundBuyBody.select("#bodydiv > div:nth-child(12) > div.r_cont.right > div.detail > div.txt_cont > div > div:nth-child(1) > div > table > tbody ");
        //购买状态
        String buyStatus = eleBuyBody.select("tr:nth-child(1) > td:nth-child(2)").text();
        //赎回状态
        String redStatus = eleBuyBody.select("tr:nth-child(1) > td:nth-child(4)").text();

        Elements tables = fundBuyBody.select("#bodydiv > div:nth-child(12) > div.r_cont.right > div.detail > div.txt_cont > div.txt_in > div.box");
        String buyStatusA = "申购费率（前端）";
        String buyStatusC = "申购费率（后端）";
        String redStatusAC = "赎回费率";
        for (Element tab : tables) {
            String text = tab.select("div > h4 > label.left").text();
            //申购费率（前端）
            if (buyStatusA.equals(text)) {
                Elements trs = tab.select("div > table > tbody > tr");
                for (Element tr : trs) {
                    String suitAmount = tr.select("td:nth-child(1)").text();
                    String suitPeriod = tr.select("td:nth-child(2)").text();
                    String suitFee = tr.select("td:nth-child(3)").text();
                    log.info(suitAmount+"\t"+suitPeriod+"\t"+suitFee+"\t");
                }
            }
            //申购费率（后端）
            if (buyStatusC.equals(text)) {
                Elements trs = tab.select("div > table > tbody > tr");
                for (Element tr : trs) {
                    String suitAmount = tr.select("td:nth-child(1)").text();
                    String suitPeriod = tr.select("td:nth-child(2)").text();
                    String suitFee = tr.select("td:nth-child(3)").text();
                    log.info(suitAmount+"\t"+suitPeriod+"\t"+suitFee+"\t");
                }
            }
            //赎回费率
            if (redStatusAC.equals(text)) {
                Elements trs = tab.select("div > table > tbody > tr");
                for (Element tr : trs) {
                    String suitAmount = tr.select("td:nth-child(1)").text();
                    String suitPeriod = tr.select("td:nth-child(2)").text();
                    String suitFee = tr.select("td:nth-child(3)").text();
                    log.info(suitAmount+"\t"+suitPeriod+"\t"+suitFee+"\t");
                }
            }
        }
    }
}
