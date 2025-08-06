package com.example.demo.service.beanLifeCycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
// 自定义Bean，实现多个生命周期接口
public class UserService implements
        BeanNameAware, BeanFactoryAware, ApplicationContextAware,
        InitializingBean, DisposableBean {

    private String name;

    // 1. 构造方法（实例化阶段调用）
    public UserService() {
        System.out.println("【1. 实例化】调用构造方法");
    }

    // 2. set方法（属性赋值阶段调用）
    public void setName(String name) {
        this.name = name;
        System.out.println("【2. 属性赋值】设置name属性：" + name);
    }

    // 3. Aware接口方法（初始化阶段-前置）
    @Override
    public void setBeanName(String s) {
        System.out.println("【3. Aware】BeanNameAware：当前Bean名称=" + s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("【3. Aware】BeanFactoryAware：获取BeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("【3. Aware】ApplicationContextAware：获取ApplicationContext");
    }

    // 4. BeanPostProcessor前置处理（由容器中的后置处理器调用）
    // （注：此方法不属于UserService，需单独定义BeanPostProcessor实现）

    // 5. 自定义初始化方法（初始化阶段-核心）
    @PostConstruct
    public void postConstruct() {
        System.out.println("【5. 初始化】@PostConstruct：执行初始化逻辑");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【5. 初始化】InitializingBean.afterPropertiesSet：属性设置完成后执行（例如：验证属性合法性）");
    }

    public void initMethod() {
        System.out.println("【5. 初始化】XML init-method：自定义初始化方法");
    }

    // 6. BeanPostProcessor后置处理（由容器中的后置处理器调用）
    // （注：此方法不属于UserService，需单独定义BeanPostProcessor实现）

    // 7. 业务方法
    public void sayHello() {
        System.out.println("Hello, " + name);
    }

    // 8. 自定义销毁方法（销毁阶段）
    @PreDestroy
    public void preDestroy() {
        System.out.println("【8. 销毁】@PreDestroy：执行销毁前逻辑");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【8. 销毁】DisposableBean.destroy：执行销毁逻辑");
    }

    public void destroyMethod() {
        System.out.println("【8. 销毁】XML destroy-method：自定义销毁方法");
    }
}
