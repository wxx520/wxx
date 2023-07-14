package com.rita.wxx.multithreads.rpc;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author lhbac
 * @create 2023/7/10 17:00
 * <p>
 * RPC IO线程：
 * 1、从业务线程发送的请求数据包队列中取数据
 * 2、负责网络连接收发数据
 * 2.1 通过TCP长连接批量发送请求报文
 * 2.2 接收服务端数据
 * 2.3 解析服务端返回的数据，拿到id，根据id找到cdl，cdl.countdown()，唤醒之前的业务线程
 */
public class LongConnManager {

    private final Map<String, CompletableFuture<String>> futureMap = new ConcurrentHashMap<>();

    private final ArrayBlockingQueue<RequestWrapper> queue = new ArrayBlockingQueue<>(100);;

    private final Socket socket;

    public LongConnManager(Socket socket) throws SocketException {
        this.socket = socket;
        socket.setKeepAlive(true);
        new Thread(new WriteThread()).start();
        new Thread(new ReadThread()).start();
    }

    public CompletableFuture<String> request(MyRpcRequest request) {
        String id = UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        futureMap.put(id, future);
        queue.add(new RequestWrapper(id, request));
        return future;
    }

    class WriteThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    RequestWrapper requestWrapper = queue.poll(1, TimeUnit.SECONDS);
                    if (requestWrapper != null) {
                        OutputStream os = socket.getOutputStream();
                        DataOutputStream dos = new DataOutputStream(os);

                        String requestId = requestWrapper.getId();
                        byte[] nts = requestId.getBytes();
                        dos.writeByte(nts.length);
                        dos.write(nts);

                        String name = requestWrapper.getRequest().getName();
                        byte[] dts = name.getBytes();
                        dos.writeShort(dts.length);
                        dos.write(dts);

                        dos.flush();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    class ReadThread implements Runnable {

        @Override
        public void run() {
            try {
                DataInput input = new DataInputStream(socket.getInputStream());
                while(true) {
                    int idLen = input.readByte();
                    byte[] idBytes = new byte[idLen];
                    input.readFully(idBytes);

                    int rspLen = input.readShort();
                    byte[] rspBytes = new byte[rspLen];
                    input.readFully(rspBytes);

                    futureMap.get(new String(idBytes)).complete(new String(rspBytes));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
