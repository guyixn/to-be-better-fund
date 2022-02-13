package com.collect_fund.eastmoney.entity.open_end_net;

import lombok.Data;

@Data
public class EastmoneyOpenEndNetListData {
    //["167301","方正富邦保险主题指数","FZFBBXZTZS","0.8250","1.3790","0.8050","1.3460","0.02","2.48","开放申购","开放赎回","","1","0","1","","1","0.08%","0.08%","1","0.80%"]
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金简称
     */
    private String fundShortName;
    /**
     * 基金简码
     */
    private String fundShortCode;
    /**
     * T日单位净值
     */
    private Double fundTUnitNet;
    /**
     * T累计净值
     */
    private Double fundTSumNet;
    /**
     * T-1日单位净值
     */
    private Double fundT_1UnitNet;
    /**
     * T-1累计净值
     */
    private Double fundT_1SumNet;
    /**
     * 日增长值
     */
    private Double dayGrowVal;
    /**
     * 日增长率
     */
    private Double dayGrowRate;
    /**
     * 申购状态
     */
    private String subscribeStatus;
    /**
     * 赎回状态
     */
    private String redeemStatus;
    /**
     * 手续费
     */
    private Double fee;
}
