package com.ruban.monitor.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.IndexTreeList;
import org.mapdb.Serializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.dao.serialize.MapDBSerialize;

@Component(value = "mapDBDao")
@Lazy
public class MapDBDaoImpl<T> implements CommonDao<T> {

    private String dbFile = "monitor.db";

    /**
     * hashmap的主key
     */
    private String primataryKey = "monitor";

    private static DB db;

    private boolean ready = false;

    @Override
    public void refresh(Properties config) {
        if (config.containsKey("dbFile")) {
            dbFile = config.getProperty("dbFile");
        }
        if (db != null && !db.isClosed()) {
            db.close();
        }
        db = DBMaker.fileDB(dbFile).checksumHeaderBypass().transactionEnable().make();

        ready = true;
    }

    @Override
    public void init(Properties config) {
        refresh(config);
    }

    /**
     * 是否已经准备好
     * 
     */
    public boolean isReady() {
        return ready;
    }

    @Override
    public void close() {
        db.close();
    }

    /**
     * 添加
     * 
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean add(String key, T value) {
        HTreeMap<String, T> treeMap = db.hashMap(primataryKey).keySerializer(Serializer.STRING).valueSerializer(new MapDBSerialize<T>())
                .createOrOpen();

        boolean result = treeMap.putIfAbsentBoolean(key, value);

        db.commit();

        return result;
    }

    /**
     * 删除 <br>
     * ------------------------------<br>
     * 
     * @param key
     */
    @Override
    public void delete(String key) {
        HTreeMap<String, T> treeMap = db.hashMap(primataryKey).keySerializer(Serializer.STRING).valueSerializer(new MapDBSerialize<T>())
                .createOrOpen();

        treeMap.remove(key);

        db.commit();
    }

    /**
     * 删除多个 <br>
     * ------------------------------<br>
     * 
     * @param keys
     */
    @Override
    public void delete(List<String> keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    /**
     * 修改 <br>
     * ------------------------------<br>
     * 
     * @param key
     * @param value
     * @return
     */
    @Override
    public void update(String key, T value) {
        HTreeMap<String, T> treeMap = db.hashMap(primataryKey).keySerializer(Serializer.STRING).valueSerializer(new MapDBSerialize<T>())
                .createOrOpen();

        treeMap.put(key, value);

        db.commit();
    }

    /**
     * 通过key获取 <br>
     * ------------------------------<br>
     * 
     * @param key
     * @return
     */
    public T get(final String key) {
        HTreeMap<String, T> treeMap = db.hashMap(primataryKey).keySerializer(Serializer.STRING).valueSerializer(new MapDBSerialize<T>())
                .createOrOpen();

        return treeMap.get(key);
    }

    @Override
    public void addToList(String key, T value) {
        IndexTreeList<T> list = db.indexTreeList(key, new MapDBSerialize<T>()).createOrOpen();

        list.add(value);

        db.commit();
    }

    @Override
    public T getFromList(String key, int index) {
        IndexTreeList<T> list = db.indexTreeList(key, new MapDBSerialize<T>()).createOrOpen();
        return list.get(index);
    }

    @Override
    public List<T> getList(String key, int start, int end) {
        IndexTreeList<T> list = db.indexTreeList(key, new MapDBSerialize<T>()).createOrOpen();

        if (list.size() < end) {
            return list;
        } else if (list.size() > start) {
            return list;
        } else {
            return list.subList(start, end);
        }
    }

    @Override
    public List<T> getAllList(String key) {
        IndexTreeList<T> list = db.indexTreeList(key, new MapDBSerialize<T>()).createOrOpen();

        return list;
    }

    @Override
    public long sizeOfList(String key) {
        IndexTreeList<T> list = db.indexTreeList(key, new MapDBSerialize<T>()).createOrOpen();
        return list.size();
    }

    @Override
    public void remOfList(String key, int start, int end) {
        IndexTreeList<T> list = db.indexTreeList(key, new MapDBSerialize<T>()).createOrOpen();

        for (int i = start; i <= end && i < list.size(); i++) {
            list.remove(i);
        }

        db.commit();
    }

    @Override
    public void removeFromList(String key, T value) {
        IndexTreeList<T> list = db.indexTreeList(key, new MapDBSerialize<T>()).createOrOpen();
        list.remove(value);

        db.commit();
    }

    @Override
    public void putToZSet(String key, long vKey, T value) {
        BTreeMap<Long, T> map = db.treeMap(key).keySerializer(Serializer.LONG).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        map.put(vKey, value);

        db.commit();
    }

    @Override
    public Map<Long, T> getFromZSet(String key, long start, long end) {
        BTreeMap<Long, T> map = db.treeMap(key).keySerializer(Serializer.LONG).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        return map.subMap(start, end + 1);
    }

    @Override
    public T getFromZSet(String key, long skey) {
        BTreeMap<Long, T> map = db.treeMap(key).keySerializer(Serializer.LONG).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        return map.get(skey);
    }

    @Override
    public T getFloorFromZSet(String key, long skey) {
        BTreeMap<Long, T> map = db.treeMap(key).keySerializer(Serializer.LONG).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        return map.floorEntry(skey).getValue();
    }

    @Override
    public boolean containsInZSet(String key, T value) {
        BTreeMap<Long, T> map = db.treeMap(key).keySerializer(Serializer.LONG).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        return map.containsValue(value);
    }

    @Override
    public void flushZSet(String key) {
        db.treeMap(key).createOrOpen().clear();
    }

    @Override
    public T removeFromZSet(String key, long skey) {
        BTreeMap<Long, T> map = db.treeMap(key).keySerializer(Serializer.LONG).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        T result = map.remove(skey);

        db.commit();

        return result;
    }

    @Override
    public <V> void putToHash(String key, V vKey, T value) {
        BTreeMap<V, T> map = db.treeMap(key).keySerializer(new MapDBSerialize<V>()).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        map.put(vKey, value);

        db.commit();
    }

    @Override
    public <V> T getFromHash(String key, V vKey) {
        BTreeMap<V, T> map = db.treeMap(key).keySerializer(new MapDBSerialize<V>()).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        return map.get(vKey);
    }

    @Override
    public <V> void removeFromHash(String key, V vKey) {
        BTreeMap<V, T> map = db.treeMap(key).keySerializer(new MapDBSerialize<V>()).valueSerializer(new MapDBSerialize<T>()).createOrOpen();

        map.remove(vKey);

        db.commit();
    }

}
