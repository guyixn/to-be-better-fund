package com.collect_fund.eastmoney.forest;

import com.dtflys.forest.annotation.*;
import com.collect_fund.eastmoney.entity.all_rank.EastmoneyAllRankResult;

/**
 * 获取所有基金排行
 */
@Address(host = "fund.eastmoney.com")
public interface FundAllRankForest {
    /**
     * 获取所有基金排行
     *
     * @return
     */
    @Get(
            url = "/data/rankhandler.aspx",
            dataType = "json",
            headers = {
                    "Referer: http://fund.eastmoney.com/data/fundranking.html",
                    "Host: fund.eastmoney.com",
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36"
            },
            data = {
                    "op=ph",
                    "dt=kf",
                    "ft=all",
                    "gs=0",
                    "sc=qjzf",
                    "st=desc",
                    "dx=0",
                    "rs=",
                    "qdii="
            },
            timeout = 60000,
            retryCount = 10,
            maxRetryInterval = 1000
    )
    EastmoneyAllRankResult getFundAllRank(@Query(name = "sd") String startDate, @Query(name = "ed") String endDate, @Query(name = "pi", defaultValue = "1") Integer pageIndex, @Query(name = "pn", defaultValue = "50") Integer pageSize);
}
