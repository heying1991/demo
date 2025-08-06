package com.example.demo.controller;

import com.example.demo.service.beanScope.BeanScopeTest;
import com.example.demo.service.beanScope.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements ApplicationContextAware {
    @Autowired
    BeanScopeTest beanScope;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/")
    public String index() {

        return "Welcome to Spring Boot Application!";
    }

    @GetMapping("/test")
    public Boolean getUSer() {
        var user1 = applicationContext.getBean("user");
        var user2 = applicationContext.getBean("user");
        return user2 == user1;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }
}