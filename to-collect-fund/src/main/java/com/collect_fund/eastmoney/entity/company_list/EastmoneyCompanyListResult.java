package com.collect_fund.eastmoney.entity.company_list;

import lombok.Data;

import java.util.List;

/**
 * @author tbb
 */
@Data
public class EastmoneyCompanyListResult {
    private List<List<String>> data;
}
