package com.ruban.learning.jvm;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ByteBufferDirectOOM {

    @Test
    public void oom() {
        try {
            ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch(Error e) {
            e.printStackTrace();
        }
    }
}
