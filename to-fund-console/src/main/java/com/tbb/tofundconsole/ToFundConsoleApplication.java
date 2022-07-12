package com.tbb.tofundconsole;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class ToFundConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToFundConsoleApplication.class, args);
    }

    /**
     * 初始化RestTemplate
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        CustomFastJsonHttpMessageConverter fastJsonHttpMessageConverter = new CustomFastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(fastJsonHttpMessageConverter);
        return new RestTemplate(messageConverters);
    }


    /**
     * 替换默认的MappingJackson2HttpMessageConverter
     * @return
     */
    public class CustomFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

        /**
         * @see org.springframework.http.HttpHeaders#setContentType 默认的 MediaType为 * /*,而这里不知哪个B加了一个异常校验
         * @return
         */
        @Override
        public List<MediaType> getSupportedMediaTypes() {
            List<MediaType> list = new ArrayList<>();
            MediaType mt = new MediaType("application", "json", StandardCharsets.UTF_8);
            list.add(mt);
            return list;
        }

        /**
         * 同上
         */
        @Override
        public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
            final HttpHeaders headers = outputMessage.getHeaders();
            addDefaultHeaders(headers, o, contentType);
            super.write(o, type, contentType, outputMessage);
        }
    }
}
