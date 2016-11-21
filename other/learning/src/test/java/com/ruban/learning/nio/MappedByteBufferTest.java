package com.ruban.learning.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 最大是2G的文件，超过无法处理
 **/
public class MappedByteBufferTest {

    public static void main(String[] args) {
        long time2 = testMapddedByteBuffer();
        long time1 = testInputStream();
        System.out.println(time1 + "-----" + time2);
    }

    public static long testMapddedByteBuffer() {
        long start = System.currentTimeMillis();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(MappedByteBufferTest.class.getResource("a.file").getFile(), "r");
            FileChannel fc = raf.getChannel();

            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            int bufferSize = 1024 * 1024;
            mbb.mark();

            byte[] b = new byte[bufferSize];
            for (int i = 0; i < fc.size(); i = i + bufferSize) {
                if (fc.size() - i < bufferSize) {
                    b = new byte[(int) fc.size() - i];
                }
                mbb.get(b);
                // System.out.print(new String(b));
            }

            mbb.reset();

            for (int i = 0; i < fc.size(); i = i + bufferSize) {
                if (fc.size() - i < bufferSize) {
                    b = new byte[(int) fc.size() - i];
                }
                mbb.get(b);
                // System.out.print(new String(b));
            }
            fc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return (System.currentTimeMillis() - start);
    }

    public static long testInputStream() {
        long start = System.currentTimeMillis();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(MappedByteBufferTest.class.getResource("a.file").getFile());

            byte[] b = new byte[1024 * 1024];

            while (fis.read(b) != -1) {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return System.currentTimeMillis() - start;
    }
}
