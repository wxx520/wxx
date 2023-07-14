package com.rita.wxx.multithreads.rpc;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author lhbac
 * @create 2023/7/10 16:43
 */
public class MyRPCClient {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        try {
            LongConnManager longConnManager = new LongConnManager(new Socket("127.0.0.1", 8888));
            List<MyRpcRequest> list = new ArrayList<>();
            for (int i = 1; i <= 100000; i++) {
                MyRpcRequest req = new MyRpcRequest();
                req.setName(getMahjongType());
                list.add(req);
            }
            List<String> resultList = list.parallelStream().map(req -> {
                CompletableFuture<String> future =  longConnManager.request(req);
                try {
                    String result = future.get(5L, TimeUnit.SECONDS);
                    System.out.println("---"+Thread.currentThread().getName()+" 结果："+result);
                    return result;
                } catch (Exception e){
                    System.out.println("---"+Thread.currentThread().getName()+"--");
                    e.printStackTrace();
                    return null;
                }
            }).toList();

            System.out.println(Arrays.toString(resultList.toArray()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMahjongType() {
        int typeNum = random.nextInt(4);
        return switch (typeNum) {
            case 2 -> "蕲州放炮麻将";
            case 3 -> "太子二五八麻将";
            default -> "广东麻将";
        };
    }
}
