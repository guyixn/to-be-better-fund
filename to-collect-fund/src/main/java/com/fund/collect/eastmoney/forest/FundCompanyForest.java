package com.fund.collect.eastmoney.forest;


import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Get;
import com.fund.collect.eastmoney.entity.company.EastmoneyCompanyResult;

/**
 * 基金公司列表
 */
@Address(host = "fund.eastmoney.com")
public interface FundCompanyForest {

    /**
     * 获取eastmoney基金公司列表
     * @return
     */
    @Get(
            url = "/Data/FundRankScale.aspx",
            dataType = "json"
    )
    EastmoneyCompanyResult getCompanyList();
}
