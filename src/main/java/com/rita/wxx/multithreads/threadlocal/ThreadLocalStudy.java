package com.rita.wxx.multithreads.threadlocal;

/**
 * @author lhbac
 * @create 2023/6/17 22:20
 */
public class ThreadLocalStudy {

    private final ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    private final ThreadLocal<Integer> wxx = ThreadLocal.withInitial(() -> 0);

    public void increment() {
        count.set(count.get() + 1);
    }
}
