package com.ruban.monitor.memcached.executor;

import java.util.Iterator;
import java.util.Map;

import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.bean.StatsSetting;

import net.spy.memcached.MemcachedClient;

public class SettingCommandExcutor extends CommandExcutor {

    public SettingCommandExcutor(MemcachedClient client) {
        super(client);

        setCmd("settings");
    }

    /**
     * 解析执行stats settings命令
     * 
     */
    @Override
    public void doExecute(ServerStats serverStatus, Map<String, String> values) {
        Iterator<String> iterator = values.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();

            StatsSetting statsSetting = new StatsSetting();
            statsSetting.setKey(key);
            statsSetting.setValue(values.get(key));

            serverStatus.getSettings().add(statsSetting);
        }
    }

}
