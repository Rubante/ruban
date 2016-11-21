package com.ruban.learning.jvm;

import java.lang.reflect.Field;

import org.junit.Test;

import sun.misc.Unsafe;

public class SunUnSafeOOM {

    @Test
    public void oom() {
        try {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            Unsafe unsalfe = (Unsafe) unsafeField.get(null);
            while (true) {
                unsalfe.allocateMemory(1024 * 1024);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Error e) {// java.lang.OutOfMemoryError
            e.printStackTrace();
        }
    }
}
