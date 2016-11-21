package com.ruban.learning.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class BlockQueueTest {

    @Test
    public void testArrayBlockQueue() throws Exception {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        queue.put("" + i);
                        TimeUnit.SECONDS.sleep(5);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        String result = queue.take();
                        System.out.println(result);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    @Test
    public void testSynchronousQueue() {

        final SynchronousQueue<String> synchronousQueue = new SynchronousQueue<String>();

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        synchronousQueue.put(i + "");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        String result = synchronousQueue.take();
                        System.out.println(result);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @Test
    public void testDelayQueue() {

    }

    class DelayObject implements Delayed {

        @Override
        public int compareTo(Delayed o) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            // TODO Auto-generated method stub
            return 0;
        }

    }
}
