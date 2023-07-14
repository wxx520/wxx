package com.rita.wxx.multithreads.threadpool;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lhbac
 * @create 2023/6/17 15:48
 */
public class MyServerSocket {
    /**
     * 1、启动一个socketSever，监听特定端口的请求，拿到请求后包装成request implement Runnable，提交到线程池中
     * <p>
     * 2、新建线程池，启动
     */
    public static void main(String[] args){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 8, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

        try (ServerSocket ss = new ServerSocket(8888)) {
            System.out.println("启动服务器....");
            while (true) {
                Socket s = ss.accept();
                System.out.println("客户端:" + InetAddress.getLocalHost() + "已连接到服务器");
                executor.submit(new MyRunnableRequest(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
