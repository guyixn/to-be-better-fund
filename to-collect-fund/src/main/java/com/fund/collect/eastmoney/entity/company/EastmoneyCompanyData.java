package com.fund.collect.eastmoney.entity.company;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.Date;

@Data
public class EastmoneyCompanyData {
    //['80000080','山西证券股份有限公司','1988-07-28','19','王怡里','SXZQ','','117.15','★★★','山西证券','','2021/9/30 0:00:00']
    /**
     * 公司账号
     */
    private String companyAccount;
    /**
     * 公司代码
     */
    private String companyCode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 成立日期
     */
    private Date setDate;
    /**
     * 全部基金数量
     */
    private String fundSum;
    /**
     * 总经理
     */
    private String managerName;

    /**
     * 天相评级
     */
    private Integer tianXiangLevel;

    /**
     * 管理规模
     */
    private Double scale;

    /**
     * 截止统计日期
     */
    private Date endDate;

    /**
     * 基金公司简称
     */
    private String companyShortName;

    public void setTianXiangLevel(String tianXiangLevel) {
        if (StrUtil.isEmpty(tianXiangLevel)){
            this.tianXiangLevel = 0;
        }
        this.tianXiangLevel = StrUtil.splitToArray(tianXiangLevel,"★").length;
    }
}
