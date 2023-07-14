package com.rita.wxx.multithreads.threadpool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * @author lhbac
 * @create 2023/6/17 16:25
 */
public class MyClientSocket {
    private static final Random random = new Random(System.currentTimeMillis());

    //age 1
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try(Socket s = new Socket("127.0.0.1", 8888)) {
                //构建IO
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                //向服务器端发送一条消息

                dos.writeByte(random.nextInt());


                String name = "Rita" + random.nextInt();
                byte[] nts = name.getBytes();
                dos.writeByte(nts.length);
                dos.write(nts);


                String desc = getMahjongType();
                byte[] dts = desc.getBytes();
                dos.writeShort(dts.length);
                dos.write(dts);


                dos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
