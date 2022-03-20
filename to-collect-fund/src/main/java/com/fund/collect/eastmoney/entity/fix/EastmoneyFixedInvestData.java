package com.fund.collect.eastmoney.entity.fix;

import lombok.Data;

import java.util.Date;

/**
 * @Author: tbb
 * @Date: 2022/2/15 22:46
 * @Description:
 */
@Data
public class EastmoneyFixedInvestData {

    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金简称
     */
    private String fundShortName;

    /**
     * T日单位净值
     */
    private Double fundTUnitNet;

    /**
     * 基金日期
     */
    private Date fundDate;

    /**
     * 近1年定投
     */
    private Double fixInvest1Year;

    /**
     * 近1年单笔
     */
    private Double singleInvest1Year;

    /**
     * 近2年定投
     */
    private Double fixInvest2Year;

    /**
     * 近2年单笔
     */
    private Double singleInvest2Year;

    /**
     * 近3年定投
     */
    private Double fixInvest3Year;

    /**
     * 近3年单笔
     */
    private Double singleInvest3Year;

    /**
     * 近5年定投
     */
    private Double fixInvest5Year;

    /**
     * 近5年单笔
     */
    private Double singleInvest5Year;
}
