package com.fund.collect.eastmoney.forest;

import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

public interface FundStoreForest {

    @Post("http://localhost:7005/eastmoney/fund_history/save_batch")
    String save1(@JSONBody String converters);

    @Post("http://localhost:7006/eastmoney/fund_history/save_batch")
    String save2(@JSONBody String converters);

    @Post("http://localhost:7007/eastmoney/fund_history/save_batch")
    String save3(@JSONBody String converters);

}
