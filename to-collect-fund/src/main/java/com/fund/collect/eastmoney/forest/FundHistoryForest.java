package com.fund.collect.eastmoney.forest;

import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryResult;
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
            headers = {
                    "Referer: http://fundf10.eastmoney.com/",
                    "Host: fund.eastmoney.com",
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36"
            },
            dataType = "json",
            async = false,
            timeout = 10000,
            maxRetryInterval = 100
    )
    EastmoneyHistoryResult getFundHistory(@Query("fundCode") String fundCode, @Query("pageIndex") Integer pageIndex, @Query("pageSize") Integer pageSize);
}
