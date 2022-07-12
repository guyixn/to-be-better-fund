package com.fund.store.eastmoney.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * <p>
 * east money基金列表
 * </p>
 *
 * @author tbb
 * @since 2022-07-08
 */
@TableName("t_em_fund_list")
public class TEmFundList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 创建日期
     */
    @JSONField(name = "create_date")
    private LocalDate createDate;

    /**
     * 创建时间
     */
    @JSONField(name = "create_time")
    private LocalTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }
    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }
    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
    public LocalTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TEmFundList{" +
            "id=" + id +
            ", fundCode=" + fundCode +
            ", fundName=" + fundName +
            ", fundType=" + fundType +
            ", createDate=" + createDate +
            ", createTime=" + createTime +
        "}";
    }
}
