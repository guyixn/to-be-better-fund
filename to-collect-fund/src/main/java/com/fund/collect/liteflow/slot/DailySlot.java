package com.fund.collect.liteflow.slot;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class DailySlot{

    /**
     * 开始日期
     */
    private Date startDate = null;

    /**
     * 结束日期
     */
    private Date endDate = null;

    /**
     * 开放式基金净值数据日期
     */
    private Date openNetDate = null;

    /**
     * 基金市场概况日期
     */
    private Date marketOverviewDate = null;

    /**
     * 基金集合
     */
    private Map<String, String> fundCollection = new HashMap<>(20000);

    /**
     * 是否可以访问
     */
    private boolean canAccess = true;

    /**
     * 步骤日志
     */
    private StringBuffer stepLog = new StringBuffer();

}
