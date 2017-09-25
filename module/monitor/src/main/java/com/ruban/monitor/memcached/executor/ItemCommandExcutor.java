package com.ruban.monitor.memcached.executor;

import java.util.Iterator;
import java.util.Map;

import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.bean.StatsItem;

import net.spy.memcached.MemcachedClient;

public class ItemCommandExcutor extends CommandExcutor {

    public ItemCommandExcutor(MemcachedClient client) {
        super(client);

        setCmd("items");
    }

    /**
     * 解析执行stats items命令
     * 
     * @param cmd
     */
    @Override
    public void doExecute(ServerStats serverStatus, Map<String, String> values) {
        Iterator<String> valueIterator = values.keySet().iterator();

        while (valueIterator.hasNext()) {
            String key = valueIterator.next();

            String[] keys = key.split(":");

            if (keys != null && keys.length == 3) {
                StatsItem statsItem = new StatsItem();
                statsItem.setCommand(getCmd());
                statsItem.setKey(key);
                statsItem.setSlabId(Integer.parseInt(keys[1]));
                statsItem.setName(keys[2]);
                statsItem.setValue(values.get(key));
                serverStatus.getItems().add(statsItem);
            }
        }
    }
}
