package com.alibaba.cloud.client8081.controller;

import com.alibaba.cloud.client8081.service.IndexFeignService;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.bouncycastle.operator.MacCalculatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

    @GetMapping("/provider/sen")
    public String gat2e() {

        return "provider";
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


    @SentinelResource(value = "sen")
    @GetMapping("sen")
    public String sentinel() throws InterruptedException {

        return indexFeignService.getPort(new HashMap<>()) + "";

    }

    public String fall() {
        return "error";
    }


    @GetMapping("down")
    @SentinelResource(value = "down")
    public Integer down(Integer size){

        return indexFeignService.getPort(new HashMap<>());
    }


    public Integer fall_down(Integer size) {
        return 0;
    }


    /**
     * 热点参数限流
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("param")
    @SentinelResource(value = "hot_param",blockHandler = "hotHandler")
    public Object param(Integer p1, Integer p2){

        return "hot";
    }

    public Object hot(Integer p1, Integer p2) {
        return "fall";
    }


    public Object hotHandler(Integer p1, Integer p2, BlockException b) {
        return "hotHandler";
    }








}
