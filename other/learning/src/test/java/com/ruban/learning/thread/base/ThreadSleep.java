package com.ruban.learning.thread.base;

public class ThreadSleep extends Thread {

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("thread begin:" + start);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("thread end : " + end);

    }

    public static void main(String[] args) {
        System.out.println("main start:" + System.currentTimeMillis());
        ThreadSleep threadSleep = new ThreadSleep();
        Thread thread = new Thread(threadSleep);
        thread.start();
        System.out.println("main end : " + System.currentTimeMillis());
    }

}
