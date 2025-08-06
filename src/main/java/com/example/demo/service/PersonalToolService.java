package com.example.demo.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

@Service
public class PersonalToolService {
    @Tool("某个区域我最喜欢的城市")
    public String reconmendCiti(@P("区域") String area) {
        System.out.println("-----"+area+"-----");
        if (area.contains("jiangsu") || area.contains("江苏")) {
            return "如皋";
        } else if (area.contains("浙江")) {
            return "杭州";
        }
        return "暂时没有";
    }

}
