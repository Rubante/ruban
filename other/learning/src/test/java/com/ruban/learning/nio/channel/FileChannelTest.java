package com.ruban.learning.nio.channel;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class FileChannelTest {

    public static void main(String[] args) {
        long time1 = testInputStream();
        long time2 = testByteBuffer();
        System.out.println(time1 + "-------" + time2);
    }

    public static long testInputStream() {
        long start = System.currentTimeMillis();
        try {
            FileInputStream fis = new FileInputStream(FileChannelTest.class.getResource("a.txt").getFile());
            byte[] b = new byte[2560];
            StringBuilder sb = new StringBuilder();
            int length = fis.read(b);
            while (length != -1) {
                if (length < b.length) {
                    sb.append(new String(Arrays.copyOfRange(b, 0, length)));
                } else {
                    sb.append(new String(b));
                }
                length = fis.read(b);
            }
            fis.close();
            System.out.println(sb);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (System.currentTimeMillis() - start);
    }

    public static long testByteBuffer() {
        long start = System.currentTimeMillis();
        try {
            RandomAccessFile raf = new RandomAccessFile(FileChannelTest.class.getResource("a.txt").getFile(), "r");
            FileChannel fileChannel = raf.getChannel();

            StringBuilder sb = new StringBuilder();
            ByteBuffer bb = ByteBuffer.allocate(2560);
            byte[] b = new byte[2560];
            while (fileChannel.read(bb) != -1) {
                bb.flip();
                // 接受数据的字节数组必须与buffer大小相一致
                if (bb.limit() < b.length) {
                    // 如果每次都是声明与buffer一致的数据大小，就不会有此判断
                    b = new byte[bb.limit()];
                }
                bb.get(b);
                sb.append(new String(b, "utf-8"));
                bb.compact();
            }

            // 接受的数组与buffer不一致，导致最后有残留
            if (bb.position() != 0) {
                bb.flip();
                b = new byte[bb.limit()];
                bb.get(b);
                sb.append(new String(b));
            }

            System.out.println(sb);
            fileChannel.close();
            raf.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (System.currentTimeMillis() - start);
    }
}
