package com.fund.store.eastmoney.mapper;

import com.fund.store.eastmoney.entity.TEmFundList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * east money基金列表 Mapper 接口
 * </p>
 *
 * @author tbb
 * @since 2022-07-08
 */
public interface TEmFundListMapper extends BaseMapper<TEmFundList> {

    /**
     * 清空表的数据
     */
    void truncateTable();
}
