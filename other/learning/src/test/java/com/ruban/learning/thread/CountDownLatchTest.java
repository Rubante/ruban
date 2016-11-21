package com.ruban.learning.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CountDownLatchTest {

    @Test
    public void testCountDownLatch() {
        final CountDownLatch count = new CountDownLatch(2);

        Thread thread = new Thread() {

            @Override
            public void run() {
                count.countDown();
                System.out.println(111);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                count.countDown();
            }
        };

        thread.start();
        try {
            count.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("finishi!");
    }

    @Test
    public void testGate() {

        final CountDownLatch cdl = new CountDownLatch(1);
        final AtomicInteger ai = new AtomicInteger(1);

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        cdl.await();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(ai.getAndIncrement());
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cdl.countDown();
    }
}
