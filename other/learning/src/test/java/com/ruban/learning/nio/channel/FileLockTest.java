package com.ruban.learning.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

public class FileLockTest {

    public static void main(String[] args) {

        // 锁定
        new Thread() {
            @Override
            public void run() {
                testLock();
            }
        }.start();
        
        // 尝试读取
        read();
    }

    public static void read() {
        try {
            RandomAccessFile raf = new RandomAccessFile(FileLockTest.class.getResource("a.txt").getFile(), "r");
            FileChannel fc = raf.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(256);
            fc.read(bb);
            while (fc.read(bb) != -1) {
                bb.flip();
                byte[] b = new byte[bb.limit()];
                bb.get(b);
                bb.clear();
                System.out.print(new String(b));
            }

            fc.close();
            raf.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testLock() {
        try {
            RandomAccessFile raf = new RandomAccessFile(FileLockTest.class.getResource("a.txt").getFile(), "rw");
            FileChannel fileChannel = raf.getChannel();

            FileLock fileLock = fileChannel.tryLock();

            TimeUnit.SECONDS.sleep(10);
            
            System.out.println("解锁");
            fileLock.release();

            fileChannel.close();
            raf.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
