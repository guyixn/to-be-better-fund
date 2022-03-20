package com.fund.collect.eastmoney.entity.list;

import lombok.Data;

@Data
public class EastMoneyListData {
    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 基金名称
     */
    private String fundName;

    /**
     * 基金类型
     */
    private String fundType;
}
