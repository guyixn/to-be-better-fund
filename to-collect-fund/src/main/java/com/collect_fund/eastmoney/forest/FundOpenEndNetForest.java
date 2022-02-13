package com.collect_fund.eastmoney.forest;

import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import com.collect_fund.eastmoney.entity.open_end_net.EastmoneyOpenEndNetListResult;

/**
 * @Author: tbb
 * @Date: 2022/2/13 0:06
 * @Description: 获取开放式基金净值
 */
@Address(host = "fund.eastmoney.com")
public interface FundOpenEndNetForest {
    /**
     * 获取开放式基金净值
     *
     * @return
     */
    @Get(
            url = "/Data/Fund_JJJZ_Data.aspx",
            dataType = "json",
            headers = {
                    "Referer: http://fund.eastmoney.com/fund.html",
                    "Host: fund.eastmoney.com",
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36"
            },
            data = {
                    "t: 1",
                    "lx: 1",
                    "sort: zdf,desc",
                    "page: 3,200",
                    "dt: 1644681537740",
                    "onlySale: 0",
            }
    )
    EastmoneyOpenEndNetListResult getFundOpenEndNet(@Query(name = "page") String page);
}
