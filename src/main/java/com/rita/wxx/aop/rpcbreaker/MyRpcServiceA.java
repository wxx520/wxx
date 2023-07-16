package com.rita.wxx.aop.rpcbreaker;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class MyRpcServiceA implements ApplicationContextAware {

    private final Random random = new Random(System.currentTimeMillis());

    @Autowired
    private ApplicationContext appCtx;


    @WxxBreaker
    public String r1(String name) {
        int num = random.nextInt(20);
        if (num % 7 == 0) {
            throw new RuntimeException("mock fail Request " + num);
        }
        return String.valueOf(num);
    }

    @PostConstruct
    public void init() {
        System.out.println("post construct MyRpcServiceA ");
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
