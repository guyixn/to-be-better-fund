package com.fund.collect.eastmoney.forest;

import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Var;

/**
 * 获取基金档案
 */
@Address(host = "fundf10.eastmoney.com")
public interface FundArchiveForest {
    /**
     * 获取eastmoney基金档案
     * @return
     */
    @Get(
            url = "/jbgk_{fundCode}.html",
            contentType = "application/x-www-form-urlencoded",
            headers = {
                    "Referer: http://fund.eastmoney.com/",
                    "Host: fundf10.eastmoney.com",
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36"
            }
    )
    String getFundArchive(@Var("fundCode") String fundCode);
    /**
     * 获取eastmoney基金档案
     * @return
     */
    @Get(
            url = "/jjfl_{fundCode}.html",
            contentType = "application/x-www-form-urlencoded",
            headers = {
                    "Referer: http://fundf10.eastmoney.com/jbgk_000001.html",
                    "Host: fundf10.eastmoney.com",
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36"
            }
    )
    String getFundBuyInfo(@Var("fundCode") String fundCode);
}
