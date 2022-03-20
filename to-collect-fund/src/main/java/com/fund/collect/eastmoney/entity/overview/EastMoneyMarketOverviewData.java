package com.fund.collect.eastmoney.entity.overview;

import lombok.Data;

import java.util.Date;

@Data
public class EastMoneyMarketOverviewData {

    /**
     * 全部基金管理规模（亿元）
     */
    private Double totalAmount;

    /**
     * 全部基金数量（只）
     */
    private Long totalNum;

    /**
     * 股票型规模
     */
    private Double stockAmount;

    /**
     * 股票型数量
     */
    private Long stockNum;

    /**
     * 混合型规模
     */
    private Double mixedAmount;

    /**
     * 混合型数量
     */
    private Long mixedNum;

    /**
     * 债券型规模
     */
    private Double bondsAmount;

    /**
     * 债券型数量
     */
    private Long bondsNum;

    /**
     * 指数型规模
     */
    private Double indexAmount;

    /**
     * 指数型数量
     */
    private Long indexNum;

    /**
     * QDII规模
     */
    private Double qdiiAmount;

    /**
     * QDII数量
     */
    private Long qdiiNum;

    /**
     * 货币型规模
     */
    private Double currencyAmount;

    /**
     * 货币型数量
     */
    private Long currencyNum;

    /**
     * 截止日期
     */
    private Date endDate;
}
