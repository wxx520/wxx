package com.rita.wxx.aop.rpcbreaker;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lhbac
 * @create 2023/7/16 15:39
 */
public class MyRpcBreaker {

    private volatile BreakerState state = BreakerState.CLOSED;
    private static final int triggerOpenFailNum = 10;

    private static final int triggerCloseSuccessNum = 8;

    private static final int halfOpenTryCount = 10;

    private int failCount = 0;

    private int trySuccessCount = 0;

    private int triedCount = 0;

    private long firstFailRequestTime;

    public synchronized void process(boolean rpcResult) {
        switch (state) {
            case CLOSED -> closeProcess(rpcResult);
            case HALF_OPEN -> halfOpenProcess(rpcResult);
            case OPEN -> {}
        }
    }

    private void halfOpenProcess(boolean rpcResult) {
        triedCount++;
        if (rpcResult) {
            trySuccessCount++;
        }

        if (triedCount <= halfOpenTryCount) {
            if (trySuccessCount >= triggerCloseSuccessNum) {
                triedCount = 0;
                trySuccessCount = 0;
                state = BreakerState.CLOSED;
                System.out.println("-/-/-熔断关闭-/-/-");
            }
        } else {
            triedCount = 0;
            trySuccessCount = 0;
            state = BreakerState.OPEN;
            System.out.println("***熔断开启***");
        }
    }

    private void closeProcess(boolean rpcResult) {
        if (rpcResult) {
            return;
        }
        //如果是第一次出现请求异常则记录出错时间,或未到达阈值但是已经超过记录区间也按照第一次处理
        if (failCount == 0 || (System.currentTimeMillis() - firstFailRequestTime > 60 * 1000)) {
            firstFailRequestTime = System.currentTimeMillis();
            failCount = 1;
            return;
        }

        failCount++;

        //到达阈值则修改状态并且记录熔断开启时间
        if (failCount >= triggerOpenFailNum) {
            failCount = 0;
            state = BreakerState.OPEN;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    state = BreakerState.HALF_OPEN;
                    System.out.println("---熔断半开--");
                }
            }, 5 * 1000);
            System.out.println("***熔断开启***");
        }
    }

    public BreakerState getState() {
        return state;
    }
}
