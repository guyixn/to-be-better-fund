package com.tbb.tofundconsole.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 允许请求路径
                .allowedOriginPatterns("*")// 表示允许所有网址发起跨域请求
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")// 表示允许跨域请求的方法
                .maxAge(3600)// 表示在3600秒内不需要再发送预校验请求
                .allowCredentials(true);// 允许客户端携带验证信息，即允许携带cookie
    }


}
