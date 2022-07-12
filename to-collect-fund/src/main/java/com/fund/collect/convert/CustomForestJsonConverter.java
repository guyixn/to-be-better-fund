package com.fund.collect.convert;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dtflys.forest.converter.json.ForestJsonConverter;
import com.dtflys.forest.utils.ForestDataType;
import com.fund.collect.eastmoney.entity.company.EastmoneyCompanyResult;
import com.fund.collect.eastmoney.entity.fix.EastmoneyFixedInvestResult;
import com.fund.collect.eastmoney.entity.history.EastmoneyHistoryResult;
import com.fund.collect.eastmoney.entity.list.EastMoneyListResult;
import com.fund.collect.eastmoney.entity.openNet.EastmoneyOpenNetResult;
import com.fund.collect.eastmoney.entity.rank.EastMoneyRankResult;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tbb
 */
@Slf4j
public class CustomForestJsonConverter implements ForestJsonConverter {

    private static final Gson gson = new Gson();
    /**
     * 将源对象转换为Map对象
     *
     * @param obj 源对象
     * @return 转换后的Map对象
     */
    @Override
    public Map<String, Object> convertObjectToMap(Object obj) {
        return new HashMap<>();
    }

    /**
     * 设置日期格式
     *
     * @param format 日期格式化模板字符
     */
    @Override
    public void setDateFormat(String format) {

    }

    /**
     * 获取日期格式
     *
     * @return 日期格式化模板字符
     */
    @Override
    public String getDateFormat() {
        return "yyyy-MM-dd";
    }

    /**
     * 将源数据转换为目标类型（Type）的java对象
     *
     * @param source     源数据
     * @param targetType 目标类型 (Type对象)
     * @return 转换后的目标类型对象
     */
    @Override
    public <T> T convertToJavaObject(String source, Type targetType) {
        //基金公司
        if (EastmoneyCompanyResult.class.getName().equals(targetType.getTypeName())) {
            return JSON.parseObject("{\"data\""+source.substring(15).replace("'","\""), (Type) EastmoneyCompanyResult.class);
        }
        //基金排行
        else if (EastMoneyRankResult.class.getName().equals(targetType.getTypeName())){
            return JSON.parseObject("{\"data\"" + StrUtil.removeSuffix(source.substring(21).replace("'", "\""), ";"), (Type) EastMoneyRankResult.class);
        }
        //开放式基金净值
        else if (EastmoneyOpenNetResult.class.getName().equals(targetType.getTypeName())){
            return JSON.parseObject(source.substring(7).replace("'","\""), (Type) EastmoneyOpenNetResult.class);
        }
        //基金定投
        else if (EastmoneyFixedInvestResult.class.getName().equals(targetType.getTypeName())){
            return JSON.parseObject(StrUtil.removeSuffix(StrUtil.subSuf(source, StrUtil.indexOf(source, '{', 0, 100)), ")"), (Type) EastmoneyFixedInvestResult.class);
        }
        //历史净值
        else if (EastmoneyHistoryResult.class.getName().equals(targetType.getTypeName())){
            return gson.fromJson(source, targetType);
        }
        //基金列表
        else if (EastMoneyListResult.class.getName().equals(targetType.getTypeName())){
            return JSON.parseObject("{\"data\":"+StrUtil.removeSuffix(source.substring(8),";")+"}", (Type) EastMoneyListResult.class);
        }
        return JSON.parseObject(source,targetType);
    }

    /**
     * 将源数据转换为目标类型（Class）的java对象
     *
     * @param source     源数据
     * @param targetType 目标类型 (Class对象)
     * @param charset
     * @return 转换后的目标类型对象
     */
    @Override
    public <T> T convertToJavaObject(byte[] source, Class<T> targetType, Charset charset) {
        return (T) new EastmoneyCompanyResult();
    }

    /**
     * 将源数据转换为目标类型（Type）的java对象
     *
     * @param source     源数据
     * @param targetType 目标类型 (Type对象)
     * @param charset
     * @return 转换后的目标类型对象
     */
    @Override
    public <T> T convertToJavaObject(byte[] source, Type targetType, Charset charset) {
        return null;
    }

    /**
     * 获取当前数据转换器转换类型
     *
     * @return
     */
    @Override
    public ForestDataType getDataType() {
        return ForestDataType.createDataType("EastMoneyCompanyList", true);
    }
}
