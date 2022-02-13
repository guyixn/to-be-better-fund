package com.utils_fund;

import cn.hutool.core.util.StrUtil;

/**
 * @Author: tbb
 * @Date: 2022/2/13 0:47
 * @Description: 数据工具类
 */
public class DataUtils {
    /**
     * String转Double
     * @param value
     * @return
     */
    public static Double toDouble(String value){
        return StrUtil.isEmpty(value) ? 0.0d : Double.parseDouble(value.replace("%",""));
    }
}
