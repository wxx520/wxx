package com.rita.wxx.multithreads.reentrantlock;

/**
 * @author lhbac
 * @create 2023/6/15 15:46
 */
public class ConsumerThread implements Runnable {

    private MyConcurrentList list;

    public ConsumerThread(MyConcurrentList list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            list.get();
        }
    }
}
