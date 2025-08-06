package com.example.demo.service.beanScope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class BeanScopeTest {


    @Bean
    @Scope("prototype")
    public User user() {
        return new User();
    }
}
