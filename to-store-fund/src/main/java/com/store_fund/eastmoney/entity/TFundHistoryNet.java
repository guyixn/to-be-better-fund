package com.store_fund.eastmoney.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author tbb
 * @since 2022-02-24
 */
@TableName("t_fund_history_net")
@Data
public class TFundHistoryNet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 基金代码
     */
    @JSONField(name = "fund_code")
    private String fundCode;

    /**
     * 基金简称
     */
    @JSONField(name = "fund_short_name")
    private String fundShortName;

    /**
     * 基金日期
     */
    @JSONField(name = "fund_date")
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
     * 购买状态
     */
    @JSONField(name = "buy_status")
    private String buyStatus;

    /**
     * 赎回状态
     */
    @JSONField(name = "red_status")
    private String redStatus;

    /**
     * 分红配送
     */
    @JSONField(name = "divide")
    private String divide;

    /**
     * 创建日期
     */
    @JSONField(name = "create_date")
    private Date createDate;
}
