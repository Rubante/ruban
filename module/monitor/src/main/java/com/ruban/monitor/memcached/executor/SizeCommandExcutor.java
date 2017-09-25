package com.ruban.monitor.memcached.executor;

import java.util.Iterator;
import java.util.Map;

import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.bean.StatsSize;

import net.spy.memcached.MemcachedClient;

public class SizeCommandExcutor extends CommandExcutor {

    public SizeCommandExcutor(MemcachedClient client) {
        super(client);

        setCmd("sizes");
    }

    /**
     * 解析执行stats size命令
     * 
     */
    @Override
    public void doExecute(ServerStats serverStatus, Map<String, String> values) {

        Iterator<String> iterator = values.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();

            StatsSize statsSize = new StatsSize();
            statsSize.setKey(key);
            statsSize.setValue(values.get(key));

            serverStatus.getSizes().add(statsSize);
        }
    }

}
