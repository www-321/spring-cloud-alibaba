package com.alibaba.cloud.client8081.config;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.property.SentinelProperty;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义流控规则
 * resource	资源名，资源名是限流规则的作用对象
 * count	限流阈值
 * grade	限流阈值类型，QPS 模式（1）或并发线程数模式（0）	QPS 模式
 * limitApp	流控针对的调用来源	default，代表不区分调用来源
 * strategy	调用关系限流策略：直接、链路、关联	根据资源本身（直接）
 * controlBehavior	流控效果（直接拒绝/WarmUp/匀速+排队等待），不支持按调用关系限流	直接拒绝
 * clusterMode	是否集群限流	否
 */

public class CustomFlowRule {

    public void load() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>("127.0.0.1:8848", "DEFAULT_GROUP", "flow_controller.json", converter());

        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

    }

    public Converter<String, List<FlowRule>> converter() {
        return content -> {

            JSONArray array = JSON.parseArray(content);
            List<FlowRule> flowRules = new ArrayList<>();
            if (!CollectionUtils.isEmpty(array)) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    FlowRule flowRule = new FlowRule();
                    flowRule.setResource(obj.getString("resource"));
                    flowRule.setCount(obj.getInteger("count"));
                    flowRule.setGrade(obj.getInteger("grade"));
                    flowRules.add(flowRule);
                }

            }
            return flowRules;
        };
    }


}
