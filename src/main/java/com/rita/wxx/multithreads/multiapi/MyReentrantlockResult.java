package com.rita.wxx.multithreads.multiapi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhbac
 * @create 2023/6/17 10:39
 */
public class MyReentrantlockResult {

    private List list = new ArrayList<>();

    private int count;

    private ReentrantLock LOCK = new ReentrantLock();
    private Condition RESULT_COMPLETE = LOCK.newCondition();

    public MyReentrantlockResult(int count) {
        this.count = count;
    }

    public List getCompleteResult(){
        try {
            LOCK.lock();
            while (list.size()<count){
                RESULT_COMPLETE.await();
            }
            return list;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            LOCK.unlock();
        }
    }

    public void addResult(Object object){
        try {
            LOCK.lock();
            list.add(object);
            if (list.size()==count){
                RESULT_COMPLETE.signalAll();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            LOCK.unlock();
        }
    }
}
