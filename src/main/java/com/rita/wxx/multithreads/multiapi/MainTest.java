package com.rita.wxx.multithreads.multiapi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author lhbac
 * @create 2023/6/17 10:58
 */
public class MainTest {

    public static void main(String[] args) {
        int count = 5;
        MyReentrantlockResult result = new MyReentrantlockResult(count);
        long ct = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(new RequestThread(result)).start();
        }
        List list = result.getCompleteResult();
        System.out.println("***"+Thread.currentThread().getName()+", costTime = "+(System.currentTimeMillis()-ct));
        System.out.println(list);

    }

    public void f() throws ExecutionException, InterruptedException {
        MyReentrantlockResult result = new MyReentrantlockResult(10);
        CompletableFuture a = CompletableFuture.runAsync(new RequestThread(result));
        a.get();

        CompletableFuture b = CompletableFuture.supplyAsync(this::h);
        b.get();

        List<String> requests = new ArrayList<>();
        List<Integer> results = requests.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> g(s)))
                .map(CompletableFuture::join)
                .toList();
    }

    public Integer g(String s) {
        return 2;
    }

    public Integer h() {
        return 2;
    }
}
