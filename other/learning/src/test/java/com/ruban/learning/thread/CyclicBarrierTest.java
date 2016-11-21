package com.ruban.learning.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

public class CyclicBarrierTest {

    @Test
    public void testCyclicBarrierSimple() {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                    System.out.println("finishi A");
                } catch (BrokenBarrierException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };

        thread1.start();

        Thread thread2 = new Thread() {

            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                    System.out.println("finishi B!");
                } catch (BrokenBarrierException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread2.start();

        System.out.println("all finishied !");
    }

    @Test
    public void testCyclicBarrier() {

        final CyclicBarrier cb = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("All Enter！");
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("enter A");
                try {
                    cb.await();
                } catch (BrokenBarrierException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("exist A");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("enter B");
                try {
                    cb.await();

                } catch (BrokenBarrierException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("exist B");
            }
        });

        thread2.start();

        thread2.start();
    }
}
