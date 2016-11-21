package com.ruban.learning.basic;

import java.util.HashMap;
import java.util.UUID;

public class HashMapMultiThread {

    public static void main(String[] args) {
        final HashMap<String, String> result = new HashMap<String, String>(2);

        Thread entry = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 20000; i++) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            result.put(UUID.randomUUID().toString(), "");
                            System.out.println(result.keySet().size());
                        }
                    }, "23k2k" + i);
                    thread.start();
                }
            }
        }, "test");
        try {
            entry.start();
            entry.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
