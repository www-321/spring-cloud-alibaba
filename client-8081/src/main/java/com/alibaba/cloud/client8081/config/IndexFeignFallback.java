package com.alibaba.cloud.client8081.config;

import com.alibaba.cloud.client8081.service.IndexFeignService;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class IndexFeignFallback implements IndexFeignService {
    @Override
    public Integer getPort(Map<String, Object> param) {
        return 0;
    }

    @Override
    public Integer postPort(Map<String, Object> param) {
        return 0;
    }
}
