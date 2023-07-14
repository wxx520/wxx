package com.rita.wxx.multithreads.reentrantlock;

import com.rita.wxx.multithreads.Mahjong;
import com.rita.wxx.multithreads.MahjongType;

import java.util.Random;

/**
 * @author lhbac
 * @create 2023/6/15 15:23
 */
public class ProducerThread implements Runnable {
    private MyConcurrentList list;
    private static final Random random = new Random(System.currentTimeMillis());

    public ProducerThread(MyConcurrentList list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            Mahjong mahjong = new Mahjong(getMahjongType());
            list.add(mahjong);
        }
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
