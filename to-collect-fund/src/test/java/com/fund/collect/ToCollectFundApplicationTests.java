package com.fund.collect;

import com.fund.collect.liteflow.slot.DailySlot;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.script.*;

import java.util.Map;
import java.util.TreeMap;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

@SpringBootTest
class ToCollectFundApplicationTests {
    private String fundCodeIndex = "fund-code";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedissonReactiveClient redissonReactiveClient;

    @Autowired
    ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    private FlowExecutor flowExecutor;

    @Test
    void getAllFundHistorySync() throws InterruptedException {
        System.out.println(redissonClient.isShutdown());
        redissonClient.getConfig();
        LiteflowResponse response = flowExecutor.execute2Resp("fundDailyChain", "123", DailySlot.class);
        System.out.println(response.getSlot().getChainName());

    }


}
