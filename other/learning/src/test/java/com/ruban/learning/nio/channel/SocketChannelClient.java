package com.ruban.learning.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.junit.Test;

public class SocketChannelClient {

    @Test
    public void client() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(8000));

            ByteBuffer bb = ByteBuffer.allocate(256);
            while (socketChannel.read(bb) != -1) {
                bb.flip();
                byte[] b = new byte[bb.limit()];
                bb.get(b);
                bb.compact();
                System.out.println(new String(b));
                
                ByteBuffer wrtieBuffer = ByteBuffer.wrap("我是客户端".getBytes());

                socketChannel.write(wrtieBuffer);
                wrtieBuffer.clear();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
