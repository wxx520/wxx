package com.rita.wxx.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lhbac
 * @create 2023/6/10 11:00
 */
@Component
@Aspect
public class WxxLogAspect {

    @Pointcut(value = "within(com.rita.wxx.aop.springaop..*) && execution(public * logParameter(..)) && args(name,..)", argNames = "name")
    public void logParameterPointcut(String name) { }

    @Before(value = "logParameterPointcut(name)", argNames = "name")
    public void beforeSayHello(String name) {

        System.out.println("before sayHello " + name);
    }

    @After(value = "logParameterPointcut(name)", argNames = "name")
    public void afterSayHello(String name) {

        System.out.println("after say hello " + name);
    }

    @Around(value = "logParameterPointcut(name)", argNames = "name")
    public Object aroundSayHello(ProceedingJoinPoint pjp, String name) throws Throwable {
        long current = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        long usedTime = System.currentTimeMillis() - current;
        System.out.println("aroud sayHello advice executed " + usedTime);
        return retVal;
    }

    @Before(value = "within(com.rita.wxx.aop.springaop..*) && execution(public * innerInvoke(..))")
    public void beforeInnerInvoke() {
        System.out.println("------------before innerInvoke--------------");
    }

}
