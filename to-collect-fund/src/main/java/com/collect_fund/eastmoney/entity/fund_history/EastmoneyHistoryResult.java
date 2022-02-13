package com.collect_fund.eastmoney.entity.fund_history;

import lombok.Data;

/**
 *
 */
@Data
public class EastmoneyHistoryResult {
    /**
     * 返回的数据体
     */
    EastmoneyHistoryData Data;
    /**
     * 错误代码
     */
    private String ErrCode;
    /**
     * 错误信息
     */
    private String ErrMsg;
    /**
     * 总共的数据
     */
    private Integer TotalCount;
    /**
     * 扩大
     */
    private String Expansion;
    /**
     * 分页索引
     */
    private Integer PageIndex;
    /**
     * 页大小
     */
    private Integer PageSize;
}
