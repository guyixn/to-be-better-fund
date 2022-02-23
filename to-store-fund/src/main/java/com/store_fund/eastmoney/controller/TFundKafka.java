package com.store_fund.eastmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: tbb
 * @Date: 2022/2/17 23:31
 * @Description:
 */
@RestController
public class TFundKafka {
    @Autowired
    private KafkaTemplate<Object, Object> template;

    @PostMapping(path = "/send/foo/{what}")
    public Foo1 sendFoo(@PathVariable String what) {
        Foo1 foo1 = new Foo1(what);
        this.template.send("topic1", foo1);
        return foo1;
    }

}
