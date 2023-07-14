package com.rita.wxx.multithreads.reentrantlock;

import com.rita.wxx.multithreads.Mahjong;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhbac
 * @create 2023/6/15 22:08
 */
public class MyConcurrentList {

    private List<Mahjong> list = new ArrayList<>();

    private int maxSize = 10;

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition EMPTY = LOCK.newCondition();

    private static final Condition FULL = LOCK.newCondition();

    public MyConcurrentList(int maxSize) {
        this.maxSize = maxSize;
    }

    public MyConcurrentList() {
    }

    public void add(Mahjong mahjong) {
        try {
            LOCK.lock();
            while (list.size() >= maxSize) {
                System.out.println(Thread.currentThread().getName() + " ****Product enough，begin waiting" + list.size());
                FULL.await();
                System.out.println(Thread.currentThread().getName() + " ++++Product enough：waited");
            }
            System.out.println(Thread.currentThread().getName() + " Producing：listSize = " + list.size());
            list.add(mahjong);
            System.out.println(Thread.currentThread().getName() + " Produced：" + mahjong + ", listSize = " + list.size());
            EMPTY.signalAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    public Mahjong get() {
        try {
            LOCK.lock();
            while (list.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " ****Consume over, begin waiting: " + list.size());
                EMPTY.await();
                System.out.println(Thread.currentThread().getName() + " ++++Consume over, waited ");
            }

            System.out.println(Thread.currentThread().getName() + " Consuming, listSize = " + list.size());
            Mahjong mahjong = list.remove(0);
            System.out.println(Thread.currentThread().getName() + " Consumed：" + mahjong + ", listSize = " + list.size());
            FULL.signalAll();
            return mahjong;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    public int size(){
        return list.size();
    }
}
