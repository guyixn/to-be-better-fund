package com.collect_fund.eastmoney.entity.all_rank;

import lombok.Data;

import java.util.List;

/**
 * @Author: tbb
 * @Date: 2022/2/12 21:04
 * @Description: all rank result list
 */
@Data
public class EastmoneyAllRankListResult {
    private List<String> data;
    private Integer allRecords = 0;
    private Integer pageIndex = 0;
    private Integer pageNum = 0;
    private Integer allPages = 0;
    private Integer allNum = 0;
    private Integer gpNum = 0;
    private Integer hhNum = 0;
    private Integer zqNum = 0;
    private Integer zsNum = 0;
    private Integer bbNum = 0;
    private Integer qdiiNum = 0;
    private Integer etfNum = 0;
    private Integer lofNum = 0;
    private Integer fofNum = 0;

    //allRecords:10038,pageIndex:3,pageNum:50,allPages:201,allNum:10038,gpNum:1986,hhNum:5534,zqNum:2307,zsNum:1473,bbNum:0,qdiiNum:211,etfNum:0,lofNum:339,fofNum:260};

}
