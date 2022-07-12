package com.fund.entity.base;

import lombok.Data;

@Data
public class BasePageQuery {

    private int page = 1;

    private int pageSize = 10;

    private String order = "ascend";
}
