package com.ruban.learning.zookeeper;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class TwoClient {

    public static void main(String[] args) throws Exception {

        final ZooKeeper zk1 = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.out.println("A111" + event.getState() + ":" + event.getType());
            }
        });
        zk1.exists("/test2", true);

        final Semaphore semaphore = new Semaphore(1);
        final ZooKeeper zk2 = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.out.println("B222" + event.getState() + ":" + event.getType());
                semaphore.release();
            }
        });
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    for (;;) {
                        semaphore.acquire();
                        zk2.exists("/test2", true);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        new Thread(run).start();

        TimeUnit.SECONDS.sleep(5);
        
        zk1.create("/test2", "elw".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        TimeUnit.SECONDS.sleep(10);
        Stat stat = new Stat();
        zk1.getData("/test2", true, stat);

        zk1.delete("/test2", -1);
        TimeUnit.SECONDS.sleep(10);

    }

}
