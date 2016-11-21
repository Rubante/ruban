package com.ruban.learning.basic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import org.junit.Test;

public class AtomicTest {

    @Test
    public void testBasicType() {
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.get());
    }

    @Test
    public void testObject() {

        User user = new User();

        user.setOld(1000);

        AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "old");

        System.out.println(updater.getAndIncrement(user));

        System.out.println(updater.get(user));
    }

    class User {
        public volatile int old;

        public int getOld() {
            return old;
        }

        public void setOld(int old) {
            this.old = old;
        }

    }

}
