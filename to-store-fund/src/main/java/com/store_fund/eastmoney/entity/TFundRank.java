package com.store_fund.eastmoney.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
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
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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

    @JSONField(name = "fund_short_name")
    private String fundShortName;

    @JSONField(name = "fund_short_code")
    private String fundShortCode;

    @JSONField(name = "fund_date")
    private LocalDate fundDate;

    @JSONField(name = "unit_net")
    private Double unitNet;

    @JSONField(name = "sum_net")
    private Double sumNet;

    @JSONField(name = "day_grow_rate")
    private Double dayGrowRate;

    @JSONField(name = "last_week")
    private Double lastWeek;

    @JSONField(name = "last1_month")
    private Double last1Month;

    @JSONField(name = "last3_month")
    private Double last3Month;

    @JSONField(name = "last6_month")
    private Double last6Month;

    @JSONField(name = "last1_year")
    private Double last1Year;

    @JSONField(name = "last2_year")
    private Double last2Year;

    @JSONField(name = "last3_year")
    private Double last3Year;

    @JSONField(name = "this_year")
    private Double thisYear;

    @JSONField(name = "fee")
    private Double fee;

    @JSONField(name = "buy_status")
    private String buyStatus;

    @JSONField(name = "create_date")
    private Date createDate;

}
