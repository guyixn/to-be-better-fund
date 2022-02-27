package com.store_fund.eastmoney.consumer;

import com.store_fund.eastmoney.entity.TFundHistoryNet;
import com.store_fund.eastmoney.entity.TFundRank;
import com.store_fund.eastmoney.service.ITFundHistoryNetService;
import com.store_fund.eastmoney.service.ITFundRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EastmoneyFundHistoryConsumer {
    @Autowired
    ITFundHistoryNetService itFundHistoryNetService;

    /**
     *
     * @param tFundHistoryNets
     */
    @KafkaListener(id = "fund-history-group-1", topics = "fund-history", concurrency = "25")
    public void listen(List<TFundHistoryNet> tFundHistoryNets) {
        itFundHistoryNetService.saveBatch(tFundHistoryNets);
        log.info("size: " + tFundHistoryNets.size());
    }
}
