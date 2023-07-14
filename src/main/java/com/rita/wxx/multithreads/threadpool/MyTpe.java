package com.rita.wxx.multithreads.threadpool;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lhbac
 * @create 2023/6/17 23:17
 */
public class MyTpe {

    private ThreadPoolExecutor executor;

    public void execute(Runnable runnable) {
        Map<ThreadLocal, Object> mainMap = get(Thread.currentThread());

        Runnable wrapper = new Runnable() {
            @Override
            public void run() {
                get(Thread.currentThread()).putAll(mainMap);
                runnable.run();
            }
        };
        executor.execute(wrapper);
    }

    private Map<ThreadLocal, Object> get(Thread t) {
        return Collections.emptyMap();
    }
}
