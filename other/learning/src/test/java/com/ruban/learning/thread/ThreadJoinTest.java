package com.ruban.learning.thread;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ThreadJoinTest {

    @Test
    public void testJoin() {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("parser 1 finishi!");
            }
        });

        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser 2 finishi!");
            }
        });

        parser1.start();
        parser2.start();
        try {
            parser1.join();
            parser2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("all finishi!");
    }

    @Test
    public void testThreadWait() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();

        System.out.println("begin :" + System.currentTimeMillis());
        try {
            synchronized (thread) {
                thread.wait(0);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("end :" + System.currentTimeMillis());
    }

    @Test
    public void testWaitIntrerupted() {
        final Object obj = new Object();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("synchronized start");
                synchronized (obj) {
                    try {
                        obj.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                System.out.println("synchronized end!");
            }
        });

        thread.start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        thread.interrupt();
    }
}
