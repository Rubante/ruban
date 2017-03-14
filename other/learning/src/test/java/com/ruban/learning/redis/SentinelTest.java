package com.ruban.learning.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class SentinelTest {

    public static void main(String[] args) {

        Set<String> sentinels = new HashSet<>();
        sentinels.add("139.199.189.214:26379");
        sentinels.add("139.199.189.214:26380");
        sentinels.add("139.199.189.214:26381");

        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);

        Jedis jedis = pool.getResource();

        try {
            for (int i = 1; i < 1000; i++) {
                jedis.set(i + "", i + "");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}