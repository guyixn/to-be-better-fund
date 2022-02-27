package com.store_fund.eastmoney.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 基金公司
 * </p>
 *
 * @author tbb
 * @since 2022-02-13
 */
@TableName("t_fund_company")
@Data
public class TFundCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司代码
     */
    @JSONField(name = "company_code")
    private String companyCode;

    /**
     * 公司名称
     */
    @JSONField(name = "company_name")
    private String companyName;

    /**
     * 成立日期
     */
    @JSONField(name = "set_date")
    private LocalDate setDate;

    /**
     * 全部管理规模(亿元)
     */
    @JSONField(name = "fund_sum")
    private Double fundSum;

    /**
     * 总经理
     */
    @JSONField(name = "manager_name")
    private String managerName;

    /**
     * 公司url
     */
    @JSONField(name = "company_web_url")
    private String companyWebUrl;

    /**
     * 天相评级
     */
    @JSONField(name = "STAR")
    private Double star;

    /**
     * 创建日期
     */
    @JSONField(name = "create_date")
    private LocalDate createDate;

}
