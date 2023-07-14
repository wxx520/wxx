package com.rita.wxx.multithreads.multiapi;

import com.rita.wxx.multithreads.Mahjong;
import com.rita.wxx.multithreads.MahjongType;

import java.util.Random;

/**
 * @author lhbac
 * @create 2023/6/15 15:23
 */
public class RequestThread implements Runnable {
    private MyReentrantlockResult result;
    private static final Random random = new Random(System.currentTimeMillis());

    public RequestThread(MyReentrantlockResult result) {
        this.result = result;
    }

    @Override
    public void run() {
        long ct = System.currentTimeMillis();
        Mahjong mahjong = new Mahjong(getMahjongType());
        try {
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        result.addResult(mahjong);
        System.out.println(Thread.currentThread().getName()+", costTime = "+(System.currentTimeMillis()-ct));
    }

    private MahjongType getMahjongType() {
        int typeNum = random.nextInt(4);
        switch (typeNum) {
            case 2:
                return MahjongType.QI_ZHOU;
            case 3:
                return MahjongType.ER_WU_BA;
            default:
                return MahjongType.GUAN_DONG;
        }
    }
}
