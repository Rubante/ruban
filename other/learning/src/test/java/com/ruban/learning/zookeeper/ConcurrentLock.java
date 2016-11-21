package com.ruban.learning.zookeeper;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ConcurrentLock {
    public static void main(String[] args) {
        final AtomicInteger atomic = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {

            Runnable run = new Runnable() {

                @Override
                public void run() {
                    final String threadName = Thread.currentThread().getName();
                    final Semaphore semaphore = new Semaphore(1);
                    ZooKeeper zk = null;
                    try {

                        zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

                            @Override
                            public void process(WatchedEvent event) {
                                if (event.getState() == KeeperState.SyncConnected) {
                                    if (event.getType() == EventType.NodeDeleted) {
                                        System.out.println(threadName + ":" + event.getType());
                                        semaphore.release();
                                    }
                                }
                            }
                        });

                        TimeUnit.SECONDS.sleep(5);
                    } catch (IOException ex) {
                        // System.out.println(1);
                        // ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    for (;;) {
                        try {
                            Stat statE = zk.exists("/lock", true);
                            if (statE != null) {
                                continue;
                            }
                            
                            
                            zk.create("/lock", ("lock" + atomic.get() + "").getBytes(), Ids.OPEN_ACL_UNSAFE,
                                    CreateMode.EPHEMERAL);
                            Stat stat = new Stat();
                            byte[] bytes = zk.getData("/lock", false, stat);
                            atomic.incrementAndGet();
                            System.out.println("get lock:" + new String(bytes));

                            zk.close();
                            break;

                        } catch (InterruptedException ex) {
                            // System.out.println(2);
                            // ex.printStackTrace();
                        } catch (KeeperException ex) {
                            // ex.printStackTrace();
                            // System.out.println(3);
                        }
                    }
                }
            };
            Thread thread = new Thread(run);
            thread.setName(i + "");
            thread.start();
        }

    }
}
