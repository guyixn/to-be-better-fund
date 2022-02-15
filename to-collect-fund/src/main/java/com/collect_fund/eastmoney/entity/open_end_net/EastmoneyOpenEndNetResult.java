package com.collect_fund.eastmoney.entity.open_end_net;

import lombok.Data;

import java.util.List;

/**
 * @author tbb
 */
@Data
public class EastmoneyOpenEndNetResult {
    private List<String> chars;
    private List<List<String>> datas;
    private String[] count;
    private String record;
    private String pages;
    private String curpage;
    private Integer[] indexsy;
    private String[] showday;
}
