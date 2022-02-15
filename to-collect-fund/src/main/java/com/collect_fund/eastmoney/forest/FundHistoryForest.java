package com.collect_fund.eastmoney.forest;

import com.collect_fund.eastmoney.entity.fund_history.EastmoneyHistoryResult;
import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;

/**
 * @Author: tbb
 * @Date: 2022/2/13 23:10
 * @Description: 基金历史净值
 */
@Address(host = "api.fund.eastmoney.com")
public interface FundHistoryForest {

    /**
     * 获取基金历史净值
     * @param fundCode
     * @param pageIndex
     * @param pageSize
     */
    @Get(
            url = "/f10/lsjz",
            headers = "Referer: http://fundf10.eastmoney.com/",
            dataType = "json",
            async = false,
            timeout = 10000,
            maxRetryInterval = 100
    )
    EastmoneyHistoryResult getFundHistory(@Query("fundCode") String fundCode, @Query("pageIndex") Integer pageIndex, @Query("pageSize") Integer pageSize);
}
