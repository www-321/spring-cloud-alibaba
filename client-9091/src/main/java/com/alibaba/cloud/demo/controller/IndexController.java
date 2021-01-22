package com.alibaba.cloud.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("port")
    public Integer port1(JSONObject jsonObject) {
        System.out.println(jsonObject);
        return port;
    }
}
