package com.rita.wxx.multithreads;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

/**
 * @author lhbac
 * @create 2023/6/15 15:27
 */

public class Mahjong {

    @Setter
    @Getter
    private MahjongType type;

    private static final Random random = new Random(System.currentTimeMillis());

    public Mahjong(MahjongType type) {
        this.type = type;
    }

    public Mahjong() {
    }

    @Override
    public String toString() {
        return "Mahjong{" +
                "type=" + type +
                '}';
    }

    public static MahjongType genMahjongType() {
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
