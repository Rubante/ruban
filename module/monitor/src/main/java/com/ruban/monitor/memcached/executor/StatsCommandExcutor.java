package com.ruban.monitor.memcached.executor;

import java.util.Iterator;
import java.util.Map;

import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.bean.Stats;

import net.spy.memcached.MemcachedClient;

public class StatsCommandExcutor extends CommandExcutor {

    public StatsCommandExcutor(MemcachedClient client) {
        super(client);
        
        setCmd("stats");
    }

    /**
     * 解析执行stats命令
     * 
     */
    @Override
    public void doExecute(ServerStats serverStatus, Map<String, String> values) {
        Iterator<String> iterator = values.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();

            Stats statsBean = new Stats();
            statsBean.setKey(key);
            statsBean.setValue(values.get(key));

            serverStatus.getStats().add(statsBean);
        }
    }

}
