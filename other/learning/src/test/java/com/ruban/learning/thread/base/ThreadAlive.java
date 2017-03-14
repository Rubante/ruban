package com.ruban.learning.thread.base;

public class ThreadAlive extends Thread {

    @Override
    public void run() {
        System.out.println("thread:" + this.isAlive());
        System.out.println("current:" + Thread.currentThread().isAlive());
    }

    public static void main(String[] args) {
        ThreadAlive alive = new ThreadAlive();
        Thread thread = new Thread(alive);
        System.out.println("main:" + Thread.currentThread().isAlive());
        thread.start();
        System.out.println("alive:" + alive.isAlive());
    }

}
