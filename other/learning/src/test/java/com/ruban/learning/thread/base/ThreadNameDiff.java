package com.ruban.learning.thread.base;

public class ThreadNameDiff extends Thread {

    @Override
    public void run() {
        super.run();
        System.out.println("this.getName:" + this.getName());
        System.out.println("Thread.current.getName:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        ThreadNameDiff diff = new ThreadNameDiff();
        diff.setName("test");
        Thread thread = new Thread(diff);
        thread.start();
        System.out.println("main:" + Thread.currentThread().getName());

    }

}
