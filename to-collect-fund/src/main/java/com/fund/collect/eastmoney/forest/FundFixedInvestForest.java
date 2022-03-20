package com.fund.collect.eastmoney.forest;

import com.fund.collect.eastmoney.entity.fix.EastmoneyFixedInvestResult;
import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;

/**
 * @Author: tbb
 * @Date: 2022/2/15 22:19
 * @Description: 基金定投
 */
@Address(host = "fund.eastmoney.com")
public interface FundFixedInvestForest {
    /**
     * 基金定投
     * @return
     */
    @Get(
            url = "/api/Dtbj.ashx",
            dataType = "json",
            headers = {
                    "Referer: http://fund.eastmoney.com/dingtou/dtbj.html",
                    "Host: fund.eastmoney.com",
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36"
            },
            data = {
                    "t=0",
                    "c=dwjz",
                    "s=desc",
                    "callback=jQuery183011668818346189402_1644935082461"
            }
    )
    EastmoneyFixedInvestResult getFundFixedInvest(@Query("page") Integer page, @Query("psize") Integer pageSize);
}
