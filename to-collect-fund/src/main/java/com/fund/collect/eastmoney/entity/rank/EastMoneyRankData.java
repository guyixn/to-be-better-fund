package com.fund.collect.eastmoney.entity.rank;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @Author: tbb
 * @Date: 2022/2/12 21:03
 * @Description: all rank pojo
 */
@Data
public class EastMoneyRankData {
    public final static String TOPIC = "fund-all-rank";
    /**
     * index 0 基金代码
     */
    @JSONField(name = "fund_code")
    private String fundCode;
    /**
     * index 1 基金简称
     */
    @JSONField(name = "fund_short_name")
    private String fundShortName;
    /**
     * index 2 基金简码
     */
    @JSONField(name = "fund_short_code")
    private String fundShortCode;
    /**
     * index 3 日期
     */
    @JSONField(name = "fund_date")
    private Date fundDate;
    /**
     * index 4 单位净值
     */
    @JSONField(name = "unit_net")
    private Double unitNet;
    /**
     * index 5 累计净值
     */
    @JSONField(name = "sum_net")
    private Double sumNet;
    /**
     * index 6 日增长率
     */
    @JSONField(name = "day_grow_rate")
    private Double dayGrowRate;
    /**
     * index 7 近一周
     */
    @JSONField(name = "last_week")
    private Double lastWeek;
    /**
     * index 8 近1月
     */
    @JSONField(name = "last1_month")
    private Double last1Month;
    /**
     * index 9 近3月
     */
    @JSONField(name = "last3_month")
    private Double last3Month;
    /**
     * index 10 近6月
     */
    @JSONField(name = "last6_month")
    private Double last6Month;
    /**
     * index 11 近1年
     */
    @JSONField(name = "last1_year")
    private Double last1Year;
    /**
     * index 12 近2年
     */
    @JSONField(name = "last2_year")
    private Double last2Year;
    /**
     * index 13 近3年
     */
    @JSONField(name = "last3_year")
    private Double last3Year;
    /**
     * index 14 今年来
     */
    @JSONField(name = "this_year")
    private Double thisYear;
    /**
     * index 15 成立来
     */
    @JSONField(name = "since_establish")
    private Double sinceEstablish;
    /**
     * index 20 手续费
     */
    @JSONField(name = "fee")
    private Double fee;
    /**
     * index 17 购买状态
     */
    @JSONField(name = "buy_status")
    private String buyStatus;

    /**
     * 创建日期
     */
    @JSONField(name = "create_date")
    private Date createDate;
}
