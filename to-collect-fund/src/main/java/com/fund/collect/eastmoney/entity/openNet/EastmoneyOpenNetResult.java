package com.fund.collect.eastmoney.entity.openNet;

import lombok.Data;

import java.util.List;

/**
 * @author tbb
 */
@Data
public class EastmoneyOpenNetResult {
    private List<String> chars;
    private List<List<String>> datas;
    private String[] count;
    private String record;
    private String pages;
    private String curpage;
    private Integer[] indexsy;
    private String[] showday;
}
