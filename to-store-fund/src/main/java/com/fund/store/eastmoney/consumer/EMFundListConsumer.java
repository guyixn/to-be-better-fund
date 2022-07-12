package com.fund.store.eastmoney.consumer;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fund.entity.dto.EastMoneyListDataDto;
import com.fund.store.eastmoney.entity.TEmFundList;
import com.fund.store.eastmoney.service.ITEmFundListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EMFundListConsumer {

    @Autowired
    ITEmFundListService itEmFundListService;

    @KafkaListener(id = "fund-list-group-1", topics = EastMoneyListDataDto.TOPIC)
    public void listen(List<EastMoneyListDataDto> eastMoneyListDataDtos) {
        List<TEmFundList> collect = eastMoneyListDataDtos.stream().map(v -> {
            TEmFundList t = new TEmFundList();
            BeanUtils.copyProperties(v, t);
            return t;
        }).collect(Collectors.toList());
        itEmFundListService.saveBatch(collect);

    }
}
