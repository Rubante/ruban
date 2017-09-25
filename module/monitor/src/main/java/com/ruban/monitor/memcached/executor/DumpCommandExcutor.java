package com.ruban.monitor.memcached.executor;

import java.util.Iterator;
import java.util.Map;

import com.ruban.monitor.memcached.bean.Item;
import com.ruban.monitor.memcached.bean.ServerStats;

import net.spy.memcached.MemcachedClient;

public class DumpCommandExcutor extends CommandExcutor {

    /** slab索引 **/
    private int slabId;

    /** 查询条数 **/
    private int limit;

    private DumpCommandExcutor(MemcachedClient client) {
        super(client);

        setCmd("cachedump " + slabId + " " + limit);
    }

    public DumpCommandExcutor(MemcachedClient client, int slabId, int limit) {
        super(client);

        this.slabId = slabId;
        this.limit = limit;

        setCmd("cachedump " + slabId + " " + limit);
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

            Item item = new Item();
            item.setKey(key);

            String[] value = values.get(key).split(";");

            // 数据大小
            String size = value[0].substring(1);
            item.setSize(size);

            // 过期时间
            int exp = Integer.parseInt(value[1].substring(1, value[1].length() - 3));
            item.setExp(exp);

            serverStatus.getValues().add(item);
        }
    }

    public int getSlabId() {
        return slabId;
    }

    public void setSlabId(int slabId) {
        this.slabId = slabId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
