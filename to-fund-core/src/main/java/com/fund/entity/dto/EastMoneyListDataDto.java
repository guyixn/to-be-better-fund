package com.fund.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EastMoneyListData {
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
    @JSONField(name = "create_date", format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
