package com.store_fund.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * @Author: tbb
 * @Date: 2022/2/13 22:42
 * @Description: 自定义sql log
 */
@Slf4j
public class CustomLogOutImpl implements Log {
    public CustomLogOutImpl(String clazz) {
        //需要定义一个有参的构造函数
        log.warn(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        log.info("error: {}", s);
        e.printStackTrace(System.err);
    }

    @Override
    public void error(String s) {
        log.info("error: {}", s);
    }

    @Override
    public void debug(String s) {
        log.info("debug: {}", s);
    }

    @Override
    public void trace(String s) {
        log.info("trace: {}", s);
    }

    @Override
    public void warn(String s) {
        log.info("warn: {}", s);
    }
}
