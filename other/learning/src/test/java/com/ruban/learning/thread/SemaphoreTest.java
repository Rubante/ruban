package com.ruban.learning.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SemaphoreTest {

    public static void main(String[] args){
        final Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 8; i++) {
            Worker worker = new Worker();
            worker.setSemaphore(semaphore);
            worker.setNum(i);

            new Thread(worker).start();
        }

    }

    static class Worker implements Runnable {
        private Semaphore semaphore;

        private int num;

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(num + "工作开始");
                TimeUnit.SECONDS.sleep(10);
                semaphore.release();
                System.out.println(num + "工作结束");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public Semaphore getSemaphore() {
            return semaphore;
        }

        public void setSemaphore(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

    }
}
