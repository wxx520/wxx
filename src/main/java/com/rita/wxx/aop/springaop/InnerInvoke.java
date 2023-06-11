package com.rita.wxx.aop.springaop;

import org.springframework.stereotype.Component;

@Component
public class InnerInvoke {

    public void sayHello(String name) {
        System.out.println("\nHello, " + name);
        innerInvoke();
    }
    
    public void innerInvoke() {
        System.out.println("--------------------innerInvoke---------------");
    }
}
