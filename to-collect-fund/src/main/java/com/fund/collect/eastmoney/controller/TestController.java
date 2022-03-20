package com.fund.collect.eastmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/tests")
    public String tests(){
        try {
            String s = restTemplate.getForObject("http://provider/tests", String.class);
            System.out.println("restTemplate:"+s);
            return s;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return "see console";
    }
}
