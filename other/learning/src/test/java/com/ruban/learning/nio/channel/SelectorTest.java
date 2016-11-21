package com.ruban.learning.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SelectorTest {

    @Test
    public void selectorOpen() {
        try {
            Selector selector = Selector.open();

            ServerSocketChannel ssc = ServerSocketChannel.open();
            // 设置为非阻塞模式
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(8000));
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isValid()) {
                        handler(selectionKey);
                    }
                    iterator.remove();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handler(SelectionKey selectionKey) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();

            final SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            ByteBuffer rbb = ByteBuffer.allocate(256);
            sc.read(rbb);
            rbb.flip();
            System.out.println(rbb.limit());
            byte[] receiveByte = new byte[rbb.limit()];
            rbb.get(receiveByte);
            System.out.println(new String(receiveByte));
            
            ByteBuffer bb = ByteBuffer.wrap("您好！".getBytes());
            sc.write(bb);
            System.out.println("send");

            new Thread() {
                public void run() {
                    while (true) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            ByteBuffer bb = ByteBuffer.wrap("您好！".getBytes());
                            sc.write(bb);
                            System.out.println("send");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
            }.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
