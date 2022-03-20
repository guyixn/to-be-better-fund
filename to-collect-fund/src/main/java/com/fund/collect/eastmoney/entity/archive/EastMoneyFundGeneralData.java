package com.fund.collect.eastmoney.entity.archive;

import lombok.Data;

@Data
public class EastMoneyFundGeneralData {
    /**
     * 基金代码
     */
    String fundCode;
    /**
     * 基金名称
     */
    String fundName;
    /**
     * 基金简称
     */
    String fundShortName;
    /**
     * 基金代码槽
     */
    String fundSlot;
    /**
     * 基金类型
     */
    String fundType;
    /**
     * 发行日期
     */
    String issueDate;
    /**
     * 成立日期/规模
     */
    String establishDate;

    /**
     * 基金管理人
     */
    String manager;
    /**
     * 基金托管人
     */
    String trustee;
}
