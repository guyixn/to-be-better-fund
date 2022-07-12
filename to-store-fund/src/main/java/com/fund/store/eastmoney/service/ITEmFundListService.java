package com.fund.store.eastmoney.service;

import com.fund.store.eastmoney.entity.TEmFundList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * east money基金列表 服务类
 * </p>
 *
 * @author tbb
 * @since 2022-07-08
 */
public interface ITEmFundListService extends IService<TEmFundList> {
    /**
     * 清空表的数据
     */
    public void truncateTable();
}
