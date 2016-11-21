package com.ruban.learning.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

public class ConcurrentLockSequence {

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getType());
            }
        });

        // zk.create("/lock2", "lock2".getBytes(), Ids.OPEN_ACL_UNSAFE,
        // CreateMode.PERSISTENT);

        for (int i = 0; i < 10; i++) {
            zk.create("/lock2/" + String.format("%5d", i), String.format("%5d", i).getBytes(), Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
        }

        List<String> children = zk.getChildren("/lock2", true);

        for (int i = 0; i < children.size(); i++) {
            String s = children.get(i);
            System.out.println(s);
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("%05d", i));
        }
    }
}
