package com.rita.wxx.aop.springaop;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service
public class MyServiceA {

    @Autowired
    private ApplicationContext appCtx;
    
    @Value("shenxianjiejie")
    private String name;
    
    public void sayHello(String name) {

        System.out.println("Hello, " + name);
    }
    
    @Override
    public String toString() {

        return "MyServiceA [appCtx=" + appCtx + ", name=" + name + "]";
    }
    
    @PostConstruct
    public void init() {

        System.out.println("post construct A ");
    }
}
