package com.example.demo.service.beanLifeCycle;

// 定义BeanPostProcessor（后置处理器）

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserService) {
            System.out.println("【4. BeanPostProcessor】前置处理：" + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserService) {
            System.out.println("【6. BeanPostProcessor】后置处理：" + beanName);
        }
        return bean;
    }
}
