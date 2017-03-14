package com.ruban.learning.thread.base;

import java.util.Random;

public class MyThread extends Thread {

    private int num;

    @Override
    public void run() {
        super.run();
        Random random = new Random();
        System.out.println(num + ":::" + random.nextInt());
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        myThread.setNum(1);

        myThread.start();
        
        myThread.start();

        System.out.println("end!");
    }
}
