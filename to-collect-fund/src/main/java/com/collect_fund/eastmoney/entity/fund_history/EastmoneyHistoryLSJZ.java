package com.collect_fund.eastmoney.entity.fund_history;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author lovex
 */
@Data
public class EastmoneyHistoryLSJZ {

    public final static String TOPIC = "fund-history";

    /**
     * 基金代码
     */
    @JSONField(name = "fund_code")
    private String fundCode;
    /**
     * 净值日期
     */
    @JSONField(name = "fund_date")
    private String FSRQ;
    /**
     * 单位净值
     */
    @JSONField(name = "unit_net")
    private String DWJZ;
    /**
     * 累计净值
     */
    @JSONField(name = "sum_net")
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
     * 日增长率
     */
    @JSONField(name = "day_grow_rate")
    private String JZZZL;
    /**
     * 申购状态
     */
    @JSONField(name = "buy_status")
    private String SGZT;
    /**
     * 赎回状态
     */
    @JSONField(name = "red_staus")
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
     * 分红配送
     */
    @JSONField(name = "Divide")
    private String FHSP;


}
