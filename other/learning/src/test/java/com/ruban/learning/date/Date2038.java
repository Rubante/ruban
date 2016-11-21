package com.ruban.learning.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2038 {

    /**
     * 模拟2038年问题
     * 
     * @param args
     */
    public static void main(String[] args) {
        Date date = new Date();
        date.setTime((long) Integer.MAX_VALUE * 1000);
        System.out.println((long) Integer.MAX_VALUE * 1000);
        System.out.println(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

        System.out.println(format.format(date.getTime()));
    }
}
