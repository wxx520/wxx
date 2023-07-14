package com.rita.wxx.multithreads.threadpool;

import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;

/**
 * @author lhbac
 * @create 2023/6/17 16:00
 */
public class MyRunnableRequest implements Runnable {

    @Setter
    @Getter
    private Socket socket;

    public MyRunnableRequest(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("****" + Thread.currentThread().getName() + "****");
        try {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            //年龄
            int age = dis.readByte();

            //姓名
            byte nameLen = dis.readByte();
            byte[] nameBytes = new byte[((int)nameLen)];
            dis.readFully(nameBytes);
            String name = new String(nameBytes);

            //简介
            short descLen = dis.readShort();
            byte[] descBytes = new byte[((int)descLen)];
            dis.readFully(descBytes);
            String desc = new String(descBytes);

            System.out.println("人物信息：age="+age+", name="+name+", desc="+desc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("can you play Mahjong with me at " + new Date());
        System.out.println("----" + Thread.currentThread().getName() + "---");
    }

}
