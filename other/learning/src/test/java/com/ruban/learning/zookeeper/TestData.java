package com.ruban.learning.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class TestData {

    @Test
    public void testConnect() {
        final CountDownLatch cdl = new CountDownLatch(1);

        String connectString = "127.0.0.1:2181";
        try {
            ZooKeeper zk = new ZooKeeper(connectString, 90, new Watcher() {

                @Override
                public void process(WatchedEvent event) {
                    System.out.println("connection create");
                    System.out.println(event.getPath());
                    System.out.println(event.getState());

                    if (KeeperState.SyncConnected == event.getState()) {
                        System.out.println("KeeperState.SyncConnected");
                    }
                    cdl.countDown();
                }
            });
            cdl.await();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void create() {

        try {
            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

                @Override
                public void process(WatchedEvent event) {
                    System.out.println(event.getState());
                }
            });

            Stat stat = new Stat();
            zk.getData("/test", true, stat);
            System.out.println(stat.getVersion());
            System.out.println(stat.getCzxid());
            System.out.println(stat.getCversion());
            zk.delete("/test", stat.getVersion());
            String path = zk.create("/test", "mytest".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            System.out.println(path);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (KeeperException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void createAsync() {
        final CountDownLatch cdl = new CountDownLatch(1);
        try {
            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

                @Override
                public void process(WatchedEvent event) {
                    // TODO Auto-generated method stub

                }
            });

            zk.create("/test/node", "node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL,
                    new StringCallback() {

                        @Override
                        public void processResult(int rc, String path, Object ctx, String name) {
                            System.out.println(path);
                            System.out.println(ctx);
                            System.out.println(name);
                            System.out.println(rc);
                            cdl.countDown();
                        }
                    }, "I am ok");

            System.out.println("main finishi!");
            cdl.await();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void change() {
        final CountDownLatch cdl = new CountDownLatch(2);
        try {
            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

                @Override
                public void process(WatchedEvent event) {
                    System.out.println(event.getPath());
                    System.out.println(event.getState() + ":" + event.getType());
                    cdl.countDown();
                }
            });

            System.out.println(1);
            Stat stat = new Stat();

            byte[] bytes1 = zk.getData("/test/node", true, stat);
            System.out.println(new String(bytes1));

            zk.setData("/test/node", "llkklk".getBytes(), -1);

            TimeUnit.SECONDS.sleep(10);

            stat = new Stat();
            byte[] bytes = zk.getData("/test/node", true, stat);
            System.out.println(bytes.length);
            System.out.println(new String(bytes));
            cdl.await();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (KeeperException ex) {
            ex.printStackTrace();
        }

    }
}