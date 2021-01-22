package com.alibaba.cloud.client8081.controller;

import com.alibaba.cloud.client8081.service.IndexFeignService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.bouncycastle.operator.MacCalculatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private IndexFeignService indexFeignService;



    @GetMapping("/api/provider/sen")
    public String gate() {

        return "api";
    }


    @GetMapping("/api/provider/s")
    public String s() {

        return "gate";
    }


    @GetMapping("port")
    public Integer port() {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "wuquan");
        param.put("id", 1000);
        return indexFeignService.getPort(param);
    }


    @GetMapping("post")
    public Integer post() {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "wuquan");
        param.put("id", 1000);
        return indexFeignService.postPort(param);
    }


    @SentinelResource(value = "sen", fallback = "fall")
    @GetMapping("sen")
    public String sentinel() {

        return "sen";
    }

    public String fall() {
        return "error";
    }


}
