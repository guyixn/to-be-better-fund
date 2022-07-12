package com.fund.store.eastmoney.service.impl;

import com.fund.store.eastmoney.entity.TEmFundList;
import com.fund.store.eastmoney.mapper.TEmFundListMapper;
import com.fund.store.eastmoney.service.ITEmFundListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * east money基金列表 服务实现类
 * </p>
 *
 * @author tbb
 * @since 2022-07-08
 */
@Service
public class TEmFundListServiceImpl extends ServiceImpl<TEmFundListMapper, TEmFundList> implements ITEmFundListService {

    /**
     * 清空表的数据
      */
    public void truncateTable(){
        this.truncateTable();
    }

}
