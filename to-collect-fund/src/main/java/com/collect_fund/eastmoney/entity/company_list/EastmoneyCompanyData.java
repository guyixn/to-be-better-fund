package com.collect_fund.eastmoney.entity.company_list;

import lombok.Data;

import java.util.Date;

@Data
public class EastmoneyCompanyData {
    //['80000080','山西证券股份有限公司','1988-07-28','19','王怡里','SXZQ','','117.15','★★★','山西证券','','2021/9/30 0:00:00']
    private String companyCode;
    private String companyName;
    private Date setDate;
    private String fundSum;
    private String userName;
    private String code;
    private String s1;
}
