package com.ruban.learning.thread.base;

import java.util.concurrent.atomic.AtomicInteger;

public class MyRunnable implements Runnable {

    private AtomicInteger atomic;

    @Override
    public void run() {
        System.out.println("running......");
        int count = atomic.incrementAndGet();

        MyRunnable myRunnable = new MyRunnable();
        myRunnable.setAtomic(atomic);
        new Thread(myRunnable).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println(count);
    }

    public AtomicInteger getAtomic() {
        return atomic;
    }

    public void setAtomic(AtomicInteger atomic) {
        this.atomic = atomic;
    }

    public static void main(String[] args) {
        AtomicInteger atomic = new AtomicInteger();
        MyRunnable runnable = new MyRunnable();
        runnable.setAtomic(atomic);
        ThreadGroup threadGroup = new ThreadGroup("1111");
        Thread thread = new Thread(threadGroup, runnable, "test", 1);
        thread.start();
    }

}
