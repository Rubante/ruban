package com.ruban.learning.thread.base;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadId extends Thread {

    @Override
    public void run() {
        System.out.println("" + this.getId());
    }

    public static void main(String[] args) {
        ThreadId threadId = new ThreadId();
        threadId.start();

        ThreadMXBean threadMB = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMB.dumpAllThreads(false, false);

        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId() + ":" + threadInfo.getThreadName());
        }
    }

}
