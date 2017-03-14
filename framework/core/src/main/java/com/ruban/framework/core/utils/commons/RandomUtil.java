package com.ruban.framework.core.utils.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random(System.nanoTime());

    /**
     * 构造函数，禁止实例化
     */
    private RandomUtil() {
        /* nothing to do */
    }

    /**
     * 产生由大字母、小写字母和数字组合的随机数
     * 
     * @param length
     *            随机数长度
     * @return 随机数 String
     */
    public static String nextStringByHybrid(int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            /* 0 <= rand < 62 */
            int rand = random.nextInt(62);
            if (rand < 26)/* 0 <= rand < 26 */ {
                /* A - Z */
                buffer.append((char) (rand + 'A'));
            } else if (rand >= 26 && rand < 52)/* 26 <= rand < 52 */ {
                /* a - z */
                buffer.append((char) (rand - 26 + 'a'));
            } else/* (52 <= rand < 62) */ {
                /* 0 - 9 */
                buffer.append((char) (rand - 52 + '0'));
            }
        }
        return buffer.toString();
    }

    /**
     * 产生由数字组合的随机数
     * 
     * @param length
     *            随机数长度
     * @return 随机数 String
     */
    public static String nextStringByDigit(int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }

    /**
     * 产生由小写字母组合的随机数
     * 
     * @param length
     *            随机数长度
     * @return 随机数 String
     */
    public static String nextStringByAlphabet(int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append((char) (random.nextInt(26) + (int) ('a')));
        }
        return buffer.toString();
    }

    /**
     * 产生指随机数组
     * 
     * @param bytes
     */
    public static void nextBytes(byte[] bytes) {
        random.nextBytes(bytes);
        shuffle(bytes);
    }

    /**
     * 随机打乱数组顺序
     * 
     * @param array
     */
    public static void shuffle(byte[] array) {
        List<Byte> list = new ArrayList<Byte>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < array.length; i++) {
            array[i] = (Byte) list.get(i);
        }
    }

    /**
     * 获取更新锁
     * 
     * @return
     */
    public static String getUpdateLock() {
        String lock = System.currentTimeMillis() + RandomUtil.nextStringByDigit(6);

        return lock;
    }

    /**
     * 获得产生随机数实例
     * 
     * @return 随机数实例 Random
     */
    public static Random getInstance() {
        return random;
    }
}
