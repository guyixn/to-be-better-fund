package com.store_fund;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@SpringBootApplication
@MapperScan("com.store_fund.eastmoney.mapper")
@Slf4j
@EnableDiscoveryClient
public class ToStoreFundApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToStoreFundApplication.class, args);
    }

    /**
     * http接口使用
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // TODO: 继承WebMvcConfigurerAdapter
        // 1、需要先定义一个 convert 转换消息的对象;
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(CharsetUtil.CHARSET_UTF_8);
        fastJsonConfig.setDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        fastJsonConfig.getParserConfig().propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        //3、在convert中添加配置信息.
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }

    /**
     * kafka消息转换
     * @return
     */
    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }


}
