package com.ruban.learning.ecode;

import org.junit.Test;

public class Unicode {

    @Test
    public void ecodeBytes() {
        try {
            System.out.println("大".getBytes("utf-8").length);
            System.out.println("𪝖".getBytes("utf-8").length);
            System.out.println("a".getBytes().length);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
