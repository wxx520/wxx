package com.rita.wxx.aop.springaop;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


@Service
public class MyServiceA implements ApplicationContextAware {

    @Autowired
    private ApplicationContext appCtx;

    @Value("shenxianjiejie")
    private String name;

    public void sayHello(String name) {

        System.out.println("Hello, " + name);
    }


    @WxxLog
    public void logParameter(String name) {
        System.out.println("logParameterA, " + name);
    }

    @Override
    public String toString() {

        return "MyServiceA [appCtx=" + appCtx + ", name=" + name + "]";
    }

    @PostConstruct
    public void init() {

        System.out.println("post construct A ");
    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
