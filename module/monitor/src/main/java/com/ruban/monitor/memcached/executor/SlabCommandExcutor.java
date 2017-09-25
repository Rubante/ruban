package com.ruban.monitor.memcached.executor;

import java.util.Iterator;
import java.util.Map;

import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.bean.StatsSlab;

import net.spy.memcached.MemcachedClient;

public class SlabCommandExcutor extends CommandExcutor {

    public SlabCommandExcutor(MemcachedClient client) {
        super(client);

        setCmd("slabs");
    }

    /**
     * 解析执行stats slabs命令
     * 
     */
    @Override
    public void doExecute(ServerStats serverStatus, Map<String, String> values) {
        Iterator<String> iterator = values.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();

            String[] keys = key.split(":");

            if (keys != null && keys.length == 2) {
                StatsSlab statsSlab = new StatsSlab();
                statsSlab.setCommand(getCmd());
                statsSlab.setKey(key);
                statsSlab.setId(Integer.parseInt(keys[0]));
                statsSlab.setName(keys[1]);
                statsSlab.setValue(values.get(key));
                serverStatus.getSlabs().add(statsSlab);
            }
        }
    }
}
