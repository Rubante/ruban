package com.ruban.learning.basic;

import org.junit.Test;

public class Power2 {

    @Test
    public void testPOW() {
        for (int i = 0; i < 1000; i++) {
            if (Power2.is2POW(i)) {
                System.out.println(i + "：" + Power2.is2POW(i));
            }
        }
    }

    public static boolean is2POW(int n) {
        int a = n & (n - 1);

        if (a == 0) {
            return true;
        } else {
            return false;
        }
    }
}
