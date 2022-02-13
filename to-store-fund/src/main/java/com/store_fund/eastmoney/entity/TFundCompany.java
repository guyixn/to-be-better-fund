package com.store_fund.eastmoney.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class TFundCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
    private LocalDate setDate;

    /**
     * 全部管理规模(亿元)
     */
    private Double fundSum;

    /**
     * 总经理
     */
    private String managerName;

    /**
     * 公司url
     */
    private String companyWebUrl;

    /**
     * 天相评级
     */
    private Double star;

    /**
     * 创建日期
     */
    private LocalDate createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public LocalDate getSetDate() {
        return setDate;
    }

    public void setSetDate(LocalDate setDate) {
        this.setDate = setDate;
    }
    public Double getFundSum() {
        return fundSum;
    }

    public void setFundSum(Double fundSum) {
        this.fundSum = fundSum;
    }
    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public String getCompanyWebUrl() {
        return companyWebUrl;
    }

    public void setCompanyWebUrl(String companyWebUrl) {
        this.companyWebUrl = companyWebUrl;
    }
    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }
    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "TFundCompany{" +
            "id=" + id +
            ", companyCode=" + companyCode +
            ", companyName=" + companyName +
            ", setDate=" + setDate +
            ", fundSum=" + fundSum +
            ", managerName=" + managerName +
            ", companyWebUrl=" + companyWebUrl +
            ", star=" + star +
            ", createDate=" + createDate +
        "}";
    }
}
