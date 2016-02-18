package com.ruban.framework.core.utils.commons;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class SortUtilTest {

    @Test
    public void sort() {
        List<Integer> list = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i <= 100; i++) {
            list.add(random.nextInt(3000));
        }

        ListSortUtil.sort(list, "doubleValue");
        int first = list.get(0);
        int middle1 = list.get(50);

        ListSortUtil.reverseSort(list, "doubleValue");
        int last = list.get(list.size() - 1);
        int middle2 = list.get(50);

        Assert.assertEquals("first equals last", first, last);
        Assert.assertEquals("middle1 equals middle2", middle1, middle2);
    }
    
    @Test
    public void milli(){
        
        Date date = new Date();
        date.setTime((long)Integer.MAX_VALUE*1000);
        System.out.println((long)Integer.MAX_VALUE*1000);
        System.out.println(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        
        System.out.println(format.format(date.getTime()));
    }
}
