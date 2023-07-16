package com.rita.wxx.aop.rpcbreaker;

/**
 * @author lhbac
 * @create 2023/7/14 20:34
 */
public enum BreakerState {


    /**
     * 关闭状态
     */
    CLOSED(1, "关闭"),
    /**
     * 开启状态
     */
    OPEN(2, "打开"),

    /**
     * 半开状态
     */
    HALF_OPEN(3, "半开");

    private final int state;

    private final String desc;

    private BreakerState(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return this.state;
    }

}
