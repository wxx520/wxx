package com.rita.wxx.aop.springaop;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class MyServiceB {

    @Autowired
    private ApplicationContext appCtx;
    
    @Value("shenxianjiejie")
    private String name;
    
    @Autowired
    private MyServiceA myServiceA;
    
    @Override
    public String toString() {
        return "MyServiceB [appCtx=" + appCtx + ", name=" + name + ", myServiceA=" + myServiceA + "]";
    }
    
    @PostConstruct
    public void init() {

        System.out.println("post construct B");
    }
}
