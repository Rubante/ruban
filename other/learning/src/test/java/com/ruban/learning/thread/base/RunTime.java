package com.ruban.learning.thread.base;

public class RunTime extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int time = (int) Math.random() * 1000;
            try {
                Thread.sleep(time);
                System.out.println("run=" + Thread.currentThread().getName());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        RunTime runTime = new RunTime();

        runTime.start();

        for (int i = 0; i < 10; i++) {
            int time = (int) Math.random() * 1000;

            try {
                Thread.sleep(time);
                System.out.println("main=" + Thread.currentThread().getName());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

}
