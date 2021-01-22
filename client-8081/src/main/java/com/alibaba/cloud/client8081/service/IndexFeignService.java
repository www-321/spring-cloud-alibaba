package com.alibaba.cloud.client8081.service;

import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient("provider")
public interface IndexFeignService {

    @GetMapping("port")
    public Integer getPort(@SpringQueryMap Map<String, Object> param);


    @PostMapping(value = "post", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer postPort(Map<String, Object> param);


}
