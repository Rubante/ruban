package com.ruban.monitor.dao;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface CommonDao<T> {

    /**
     * 是否准备好
     * 
     * @return
     */
    public boolean isReady();

    /**
     * 用于建立或重建与数据库的链接
     * 
     * @param config
     */
    public void refresh(Properties config);

    /**
     * 初始化配置
     * 
     * @param config
     */
    public void init(Properties config);

    /**
     * 关闭资源
     * 
     */
    public void close();

    /**
     * 添加
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key, T value);

    /**
     * 删除 <br>
     * ------------------------------<br>
     * 
     * @param key
     */
    public void delete(String key);

    /**
     * 删除多个 <br>
     * ------------------------------<br>
     * 
     * @param keys
     */
    public void delete(List<String> keys);

    /**
     * 修改 <br>
     * ------------------------------<br>
     * 
     * @param key
     * @param value
     * @return
     */
    public void update(String key, T value);

    /**
     * 通过key获取 <br>
     * ------------------------------<br>
     * 
     * @param key
     * @return
     */
    public T get(String key);

    /**
     * 往列表中添加元素
     * 
     * @param key
     * @param value
     */
    public void addToList(String key, T value);

    /**
     * 从列表中获取元素
     * 
     * @param key
     * @param index
     */
    public T getFromList(String key, int index);

    /**
     * 获取列表
     * 
     * @param key
     * @param start
     *            :起始索引
     * @param end
     *            :结束索引
     * @return
     */
    public List<T> getList(String key, int start, int end);

    /**
     * 获取所有列表
     * 
     * @param key
     * @return
     */
    public List<T> getAllList(String key);

    /**
     * list的列表大小
     * 
     * @param key
     * @return
     */
    public long sizeOfList(String key);

    /**
     * 裁剪list
     * 
     * @param key
     * @param start
     * @param end
     */
    public void remOfList(String key, int start, int end);

    /**
     * 从列表中删除特定值
     * 
     * @param key
     * @param value
     */
    public void removeFromList(String key, T value);

    /**
     * 往ZSet表里存储值
     * 
     * @param key
     * @param vKey
     * @param value
     */
    public void putToZSet(String key, long vKey, T value);

    /**
     * 从ZSet表里获取值
     * 
     * @param key
     * @param skey
     * @param eKey
     * @return
     */
    public Map<Long, T> getFromZSet(String key, long skey, long eKey);

    /**
     * 从ZSet表里获取值
     * 
     * @param key
     * @param skey
     * @param eKey
     * @return
     */
    public T getFromZSet(String key, long skey);

    /***
     * 获取比当前skey小的值
     * 
     * @param key
     * @param skey
     * @return
     */
    public T getFloorFromZSet(String key, long skey);

    /**
     * 判断值是否在集合中
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean containsInZSet(String key, T value);

    /**
     * 清除ZSet集合
     * 
     * @param key
     * @return
     */
    public void flushZSet(String key);

    /**
     * 将元素删除
     * 
     * @param key
     * @param skey
     */
    public T removeFromZSet(String key, long skey);

    /**
     * 往hash表里存储值
     * 
     * @param key
     * @param vKey
     * @param value
     */
    public <V> void putToHash(String key, V vKey, T value);

    /**
     * 从hash表里获取值
     * 
     * @param key
     * @param vKey
     */
    public <V> T getFromHash(String key, V vKey);

    /**
     * 从hash表里选取值
     * 
     * @param key
     * @param vKey
     */
    public <V> void removeFromHash(String key, V vKey);
}
