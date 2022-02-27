package com.api_fund;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author tbb
 */
@Data
public class EastmoneyHistoryLSJZConverter {
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
    private String fundDate;

    /**
     * 单位净值
     */
    @JSONField(name = "unit_net")
    private String unitNet;

    /**
     * 累计净值
     */
    @JSONField(name = "sum_net")
    private String sumNet;

    /**
     * 日增长率
     */
    @JSONField(name = "day_grow_rate")
    private String dayGrowRate;

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
    private String createDate;
}
