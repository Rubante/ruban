package com.ruban.learning.thread.base;

/**
 * 线程的启动顺序，不代表其运行的顺序，从概率上讲，先启动的，先运行的机会大
 * 
 * @author yjwang
 *
 */
public class SequenceThread extends Thread {

    private int i = 0;

    public SequenceThread(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(i);
    }

    public static void main(String[] args) {
        
        SequenceThread thread1 = new SequenceThread(1);
        SequenceThread thread2 = new SequenceThread(2);
        SequenceThread thread3 = new SequenceThread(3);
        SequenceThread thread4 = new SequenceThread(4);
        SequenceThread thread5 = new SequenceThread(5);
        SequenceThread thread6 = new SequenceThread(6);
        SequenceThread thread7 = new SequenceThread(7);
        SequenceThread thread8 = new SequenceThread(8);
        SequenceThread thread9 = new SequenceThread(9);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();

    }

}
