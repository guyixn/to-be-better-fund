package com.fund.collect.eastmoney.entity.archive;

import lombok.Data;

@Data
public class EastMoneyFundArchiveData {
    /**
     * 基金代码
     */
    String fundCode;

    /**
     * 基金简称
     */
    String fundShortName;
    /**
     * 资产规模
     */
    String assetScale;
    /**
     * 份额规模
     */
    String shareScale;
    /**
     * 基金经理人
     */
    String managerMan;
    /**
     * 成立以来分红
     */
    String div;
    /**
     * 管理费率
     */
    String managerRate;
    /**
     * 托管费率
     */
    String escrowRate;
    /**
     * 销售服务费率
     */
    String saleServiceRate;
    /**
     * 最高认购费率
     */
    String maxSubRate;
    /**
     *最高申购费率
     */
    String maxBuyRate;
    /**
     * 最高赎回费率
     */
    String maxRedRate;
    /**
     * 业绩比较基准
     */
    String achievementBenchMark;
    /**
     * 跟踪标的
     */
    String trackingTarget;
}
