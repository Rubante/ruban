package com.ruban.learning.zookeeper;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DubboZookeeperTest {

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper("127.0.0.1", 5000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {

            }
        });

        List<String> children = zk.getChildren("/", true);
        for (String s : children) {
            System.out.println(s);
        }
    }

}
