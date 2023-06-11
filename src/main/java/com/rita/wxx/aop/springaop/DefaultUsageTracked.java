package com.rita.wxx.aop.springaop;

public class DefaultUsageTracked implements UsageTracked {

    private int usedCount = 0;
    
    @Override
    public void increment() {
        usedCount++;
    }

    @Override
    public int get() {
        return usedCount;
    }

}
