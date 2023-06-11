package com.rita.wxx.aop.springaop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UsageTrackedAspect {

    @DeclareParents(value = "com.lisp.springframework.core.aop.springaop.*+", 
            defaultImpl = DefaultUsageTracked.class)
    public static UsageTracked mixin;
    
    @Before(value = "within(com.lisp.springframework.core.aop.springaop..*) && this(mixin)", argNames = "mixin")
    public void recordUsage(UsageTracked mixin) {
        mixin.increment();
    }
}
