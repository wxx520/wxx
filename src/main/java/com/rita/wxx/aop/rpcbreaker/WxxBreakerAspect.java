package com.rita.wxx.aop.rpcbreaker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author lhbac
 * @create 2023/6/10 11:00
 */
@Component
@Aspect
public class WxxBreakerAspect {

    private final MyRpcBreaker breaker = new MyRpcBreaker();

    @Around(value = "@annotation(com.rita.wxx.aop.rpcbreaker.WxxBreaker)")
    public Object aroundSayHello(ProceedingJoinPoint pjp) throws Throwable {
        BreakerState breakerState = breaker.getState();
        switch (breakerState) {
            case OPEN -> {
                return BreakerState.OPEN.name();
            }
            case CLOSED -> {
                RPCResult rpcResult = invoke(pjp);
                breaker.process(rpcResult.isSuccess);
                return BreakerState.CLOSED.name() + " : " + rpcResult.retObject;
            }
            case HALF_OPEN -> {
                RPCResult rpcResult = invoke(pjp);
                breaker.process(rpcResult.isSuccess);
                return BreakerState.HALF_OPEN.name() + " : " + rpcResult.retObject;
            }
            default -> {
                return null;
            }
        }
    }

    private RPCResult invoke(ProceedingJoinPoint pjp) {
        try {
            Object retVal = pjp.proceed();
            return new RPCResult(true, retVal);
        } catch (Throwable e) {
            e.printStackTrace();
            return new RPCResult(false, 0);
        }
    }

    private record RPCResult(boolean isSuccess, Object retObject) {
    }
}
