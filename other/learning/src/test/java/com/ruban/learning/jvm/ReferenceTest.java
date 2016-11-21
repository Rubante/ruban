package com.ruban.learning.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ReferenceTest {

    @Test
    public void testPhantomReference() {

        try {
            TestObject to = new TestObject();

            final ReferenceQueue<TestObject> referenceQueue = new ReferenceQueue<TestObject>();

            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        Object o = referenceQueue.poll();
                        if (o != null) {
                            System.out.println("break");
                        }
                    }
                }
            }.start();

            PhantomReference<TestObject> phantom = new PhantomReference<TestObject>(to, referenceQueue);

            to = null;

            TimeUnit.SECONDS.sleep(5);

            System.out.println("收集开始！");

            System.gc();

            TimeUnit.SECONDS.sleep(5);
            
            System.out.println("收集结束！");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

class TestObject {
}
