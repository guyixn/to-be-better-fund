package com.fund.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fund.entity.base.BasePageQuery;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EastMoneyListDataDto extends BasePageQuery {
    /**
     * 主题
     */
    public final static String TOPIC = "east_money-list";

    /**
     * 基金代码
     */
    @JSONField(name = "fund_code")
    private String fundCode;

    /**
     * 基金名称
     */
    @JSONField(name = "fund_name")
    private String fundName;

    /**
     * 基金类型
     */
    @JSONField(name = "fund_type")
    private String fundType;

    /**
     * 发生日期
     */
    @JSONField(name = "create_date", format = "yyyy-MM-dd")
    private LocalDate createDate;

    /**
     * 发生时间
     */
    @JSONField(name = "create_time", format = "HH:mm:ss")
    private LocalTime createTime;
}
