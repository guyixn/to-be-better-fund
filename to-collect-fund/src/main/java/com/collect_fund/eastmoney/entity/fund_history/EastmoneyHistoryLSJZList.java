package com.collect_fund.eastmoney.entity.fund_history;

import lombok.Data;

@Data
public class EastmoneyHistoryLSJZList {
    /**
     * 净值日期
     */
    private String FSRQ;
    /**
     * 单位净值
     */
    private String DWJZ;
    /**
     * 累计净值
     */
    private String LJJZ;
    /**
     *
     */
    private String SDATE;
    /**
     *
     */
    private String ACTUALSYI;
    /**
     *
     */
    private String NAVTYPE;
    /**
     * 净值增长率
     */
    private String JZZZL;
    /**
     * 申购状态
     */
    private String SGZT;
    /**
     * 赎回状态
     */
    private String SHZT;
    /**
     *
     */
    private String FHFCZ;
    /**
     *
     */
    private String FHFCBZ;
    /**
     *
     */
    private String DTYPE;
    /**
     *
     */
    private String FHSP;

    private String FundType;
    private String SYType;
    private String isNewType;
    private String Feature;
    private String FundCode;
    private String crawlDate;
}
