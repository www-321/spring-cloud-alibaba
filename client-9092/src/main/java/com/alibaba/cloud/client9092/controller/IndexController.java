package com.alibaba.cloud.client9092.controller;

import com.alibaba.cloud.client9092.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.jnlp.UnavailableServiceException;
@RefreshScope
@RestController
public class IndexController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("port")
    public Integer port1(User user) {
        System.out.println(user.toString());
        return port;
    }



    @PostMapping("post")
    public Integer post(@RequestBody User user) {
        System.out.println(user.toString());
        return port;
    }
}
