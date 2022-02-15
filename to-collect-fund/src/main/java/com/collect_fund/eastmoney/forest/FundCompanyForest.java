package com.collect_fund.eastmoney.forest;


import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Get;
import com.collect_fund.eastmoney.entity.company_list.EastmoneyCompanyResult;

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
