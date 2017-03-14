package com.ruban.learning.thread;

public class StartThreadRun extends Thread {

    public static String str = "Data:";

    @Override
    public void run() {

        for (int i = 0; i < 4; i++) {
            str += "," + i;
        }
    }

    public static void main(String[] args) {

        StartThreadRun run = new StartThreadRun();

        run.start();

        System.out.println(StartThreadRun.str);
    }
}
