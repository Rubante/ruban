package com.ruban.learning.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class ExecutorTest {

    public static void main(String[] args) {
        final AtomicInteger ai = new AtomicInteger(1);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {

                    System.out.println("++++++++++++++++++");
                    System.out.println(ai.getAndIncrement());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            executor.execute(runnable);

        }

    }

    @Test
    public void testFixedExecutorF() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 6; i++) {
            final int index = i;
            System.out.println("task: " + (i + 1));
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread start" + index);
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread end" + index);
                }
            };
            service.execute(run);
        }
    }

    @Test
    public void testCachedExecutor() {
        final AtomicInteger ai = new AtomicInteger();

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 1000; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println(ai.getAndIncrement());
                    System.out.println(Thread.currentThread().getName());
                }

            };

            executor.execute(runnable);

        }
    }

    @Test
    public void testFutureTask() {

        final AtomicInteger ai = new AtomicInteger();

        ExecutorService executor = Executors.newCachedThreadPool();

        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                ai.getAndIncrement();
                TimeUnit.SECONDS.sleep(10);
                return String.valueOf(ai.get());
            }
        });

        Thread thread = new Thread(task);
        try {
            thread.start();
            System.out.println(task.get());
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("future task finishi");

    }

    @Test
    public void testFuture() {

        ExecutorService executors = Executors.newCachedThreadPool();
        final AtomicInteger ai = new AtomicInteger();
        Callable<Integer> callable = new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                ai.getAndIncrement();
                return ai.get();
            }
        };
        Future<Integer> future = executors.submit(callable);

        try {
            System.out.println(future.get());
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("finishi");

    }

    @Test
    public void testResultAndRunnable() {
        ExecutorService executor = Executors.newCachedThreadPool();

        Integer result = new Integer(0);
        Future<Integer> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("11111");

            }
        }, result);

        try {
            System.out.println(future.get());
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
