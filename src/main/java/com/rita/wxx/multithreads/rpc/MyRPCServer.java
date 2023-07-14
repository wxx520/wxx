package com.rita.wxx.multithreads.rpc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lhbac
 * @create 2023/7/10 17:00
 */
public class MyRPCServer {

    /**
     * 1、启动一个socketSever，监听特定端口的请求，拿到请求后包装成request implement Runnable，提交到线程池中
     * <p>
     * 2、新建线程池，启动
     */
    public static void main(String[] args){

        try (ServerSocket ss = new ServerSocket(8888)) {
            System.out.println("启动服务器....");
            Socket socket = ss.accept();
            while (true) {
                System.out.println("客户端:" + InetAddress.getLocalHost() + "已连接到服务器");
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);

                byte idLen = dis.readByte();
                byte[] idBytes = new byte[((int)idLen)];
                dis.readFully(idBytes);
                String id = new String(idBytes);

                short descLen = dis.readShort();
                byte[] descBytes = new byte[((int)descLen)];
                dis.readFully(descBytes);
                String desc = new String(descBytes);

                System.out.println("****Server : "+id+", name = "+desc);


                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                dos.writeByte(idLen);
                dos.write(idBytes);

                dos.writeShort(descLen);
                dos.write(descBytes);

                dos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
