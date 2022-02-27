package com.store_fund.eastmoney.consumer;

import com.store_fund.eastmoney.entity.TFundRank;
import com.store_fund.eastmoney.service.ITFundRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基金排行消费者
 */
@Component
@Slf4j
public class EastmoneyFundRankConsumer {

    @Autowired
    ITFundRankService itFundRankService;

    private Integer totalSize = 0;

    /**
     * @param tFundRanks
     */
    @KafkaListener(id = "fund-all-rank-group-1", topics = "fund-all-rank")
    public void listen(List<TFundRank> tFundRanks) {
        itFundRankService.saveBatch(tFundRanks);
        totalSize += tFundRanks.size();

        log.info("size: " + totalSize);
    }
}
