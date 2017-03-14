package com.ruban.learning.thread.base;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadName {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());

        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMxBean.dumpAllThreads(false, false);
        for (int j = 0; j < threadInfos.length; j++) {
            StackTraceElement[] elements = threadInfos[j].getStackTrace();

            for (int i = 0; i < elements.length; i++) {
                System.out.println(elements[i].getClassName() + ":" + elements[i].getMethodName() + ":"
                        + elements[i].getLineNumber());
            }
        }
    }

}
