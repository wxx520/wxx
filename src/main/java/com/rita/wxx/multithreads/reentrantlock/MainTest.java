package com.rita.wxx.multithreads.reentrantlock;

/**
 * @author lhbac
 * @create 2023/6/15 15:50
 */
public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        int maxSize = 10;
        MyConcurrentList list = new MyConcurrentList(maxSize);

        new Thread(new ProducerThread(list)).start();
        new Thread(new ProducerThread(list)).start();
        new Thread(new ConsumerThread(list)).start();
        new Thread(new ConsumerThread(list)).start();
        new Thread(new ConsumerThread(list)).start();
        while (true) {
            System.out.println("-----------" + list.size());
            if (list.size() > maxSize) {
                throw new RuntimeException("product too much!!!");
            }

            Thread.sleep(2000);
        }
    }
}
