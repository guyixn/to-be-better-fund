package com.fund.store.eastmoney.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.fund.store.eastmoney.entity.TFundHistoryNet;
import com.fund.store.eastmoney.mapper.TFundHistoryNetMapper;
import com.fund.store.eastmoney.service.ITFundHistoryNetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tbb
 * @since 2022-02-24
 */
@Service
@DS("shardingSphere")
public class TFundHistoryNetServiceImpl extends ServiceImpl<TFundHistoryNetMapper, TFundHistoryNet> implements ITFundHistoryNetService {

}
