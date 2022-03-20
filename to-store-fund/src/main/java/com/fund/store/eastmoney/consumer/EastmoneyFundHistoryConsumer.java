package com.fund.store.eastmoney.consumer;

import com.alibaba.fastjson.JSON;
import com.fund.store.eastmoney.entity.TFundHistoryNet;
import com.fund.store.eastmoney.service.ITFundHistoryNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EastmoneyFundHistoryConsumer {
    @Autowired
    ITFundHistoryNetService itFundHistoryNetService;

    /**
     *
     * @param tFundHistoryNets
     */
    @KafkaListener(id = "fund-history-group-7", topics = "fund-history", concurrency = "10")
    public void listen(TFundHistoryNet tFundHistoryNets) {
        synchronized (EastmoneyFundHistoryConsumer.class) {
            try {
                itFundHistoryNetService.save(tFundHistoryNets);
            } catch (Exception e) {
                log.error(JSON.toJSONString(tFundHistoryNets) + " || " + e.getMessage());

            }
        }
    }
}
