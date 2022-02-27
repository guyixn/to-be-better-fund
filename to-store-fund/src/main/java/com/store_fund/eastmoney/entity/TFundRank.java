package com.store_fund.eastmoney.entity;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 排名
 * </p>
 *
 * @author tbb
 * @since 2022-02-14
 */
@TableName("t_fund_rank")
@Data
public class TFundRank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 基金简称
     */
    @JSONField(name = "fund_code")
    private String fundCode;

    /**
     * 基金简码
     */
    @JSONField(name = "fund_short_name")
    private String fundShortName;

    /**
     * 基金公司简码
     */
    @JSONField(name = "fund_short_code")
    private String fundShortCode;

    /**
     * 基金日期
     */
    @JSONField(name = "fund_date", format = DatePattern.NORM_DATE_PATTERN)
    private Date fundDate;

    /**
     * 单位净值
     */
    @JSONField(name = "unit_net")
    private Double unitNet;

    /**
     * 累计净值
     */
    @JSONField(name = "sum_net")
    private Double sumNet;

    /**
     * 日增长率
     */
    @JSONField(name = "day_grow_rate")
    private Double dayGrowRate;

    /**
     * 上周
     */
    @JSONField(name = "last_week")
    private Double lastWeek;

    /**
     * 过去1个月
     */
    @JSONField(name = "last1_month")
    private Double last1Month;

    /**
     * 过去3个月
     */
    @JSONField(name = "last3_month")
    private Double last3Month;

    /**
     * 过去6个月
     */
    @JSONField(name = "last6_month")
    private Double last6Month;

    /**
     * 过去1年
     */
    @JSONField(name = "last1_year")
    private Double last1Year;

    /**
     * 过去2年
     */
    @JSONField(name = "last2_year")
    private Double last2Year;

    /**
     * 过去3年
     */
    @JSONField(name = "last3_year")
    private Double last3Year;

    /**
     * 今年以来
     */
    @JSONField(name = "this_year")
    private Double thisYear;

    /**
     * 成立来
     */
    @JSONField(name = "since_establish")
    private Double sinceEstablish;

    /**
     * 手续费
     */
    @JSONField(name = "fee")
    private Double fee;

    /**
     * 购买状态
     */
    @JSONField(name = "buy_status")
    private String buyStatus;

    /**
     * 创建日期
     */
    @JSONField(name = "create_date")
    private Date createDate;

}
