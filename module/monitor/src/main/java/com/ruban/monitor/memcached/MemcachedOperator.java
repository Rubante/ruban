package com.ruban.monitor.memcached;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.spy.memcached.CASResponse;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

public class MemcachedOperator {

    private static final Logger logger = LoggerFactory.getLogger(MemcachedOperator.class);

    private MemcachedClient client;

    public MemcachedClient getClient() {
        return client;
    }

    public void setClient(MemcachedClient client) {
        this.client = client;
    }

    /**
     * 保存数据，会覆盖原有的数据。新条目位于LRU的顶部
     * 
     * @param key
     * @param exp
     * @param data
     */
    public boolean set(String key, int exp, Object data) {
        OperationFuture<Boolean> future = client.set(key, exp, data);

        return getResult(future);
    }

    /**
     * 保存数据，会覆盖原有的数据，不失效
     * 
     * @param key
     * @param data
     */
    public boolean set(String key, Object data) {
        OperationFuture<Boolean> future = client.set(key, 0, data);

        return getResult(future);
    }

    /**
     * 保存数据。如果数据不存在，则添加（保存在LRU的头部），如果已经存在则会将该数据提升到LRU的顶部
     * 
     * @param key
     * @param exp
     * @param data
     * @return
     */
    public boolean add(String key, int exp, Object data) {
        OperationFuture<Boolean> future = client.add(key, exp, data);

        return getResult(future);
    }

    /**
     * 永不过期
     * 
     * @param key
     * @param data
     * @return
     */
    public boolean add(String key, Object data) {
        return add(key, 0, data);
    }

    /**
     * 将数据添加在已有数据的末尾。不能扩展原有条目的限制。主要用于管理列表
     * 
     * @param key
     * @param data
     * @return
     */
    public boolean append(String key, Object data) {
        OperationFuture<Boolean> future = client.append(key, data);

        return getResult(future);
    }

    /**
     * 添加数据到原有数据的前面
     * 
     * @param key
     * @param data
     * @return
     */
    public boolean prepend(String key, Object data) {
        OperationFuture<Boolean> future = client.prepend(key, data);

        return getResult(future);
    }

    /**
     * 延时
     * 
     * @param key
     * @param time
     * @return
     */
    public boolean touch(String key, int time) {

        OperationFuture<Boolean> future = client.touch(key, time);

        return getResult(future);
    }

    /**
     * Compare And Swap
     * 
     * @param key
     * @param casId
     * @param value
     * @return
     */
    public CASResponse cas(String key, long casId, Object value) {
        return client.cas(key, casId, value);
    }

    /**
     * Compare And Swap
     * 
     * @param key
     * @param casId
     * @param value
     * @param exp
     * @return
     */
    public CASResponse cas(String key, long casId, Object value, int exp) {
        return client.cas(key, casId, exp, value);
    }

    /**
     * 获取存储的值<br/>
     * 如果是采用默认序列化的方式的话，将被转成对应的对象，但如果对象类未加载未出现转化异常
     * 
     * @param key
     * @return
     */
    public Object get(String key) {
        return client.get(key);
    }

    /**
     * 获取存储的值
     * 
     * @param key
     * @param exp
     * @return
     */
    public Object get(String key, int exp) {
        return client.getAndTouch(key, exp);
    }

    /**
     * 批量获取存储的值
     * 
     * @param keys
     * @return
     */
    public Map<String, Object> getBulk(List<String> keys) {
        return client.getBulk(keys);
    }

    /**
     * 删除一个存储
     * 
     * @param key
     * @return
     */
    public OperationFuture<Boolean> delete(String key) {
        return client.delete(key);
    }

    /**
     * 自增存储的值，但如果不存在则将出异常
     * 
     * @param key
     * @param value
     * @return
     */
    public long incr(String key, int value) {
        return client.incr(key, value);
    }

    /**
     * 自减值，如果不存在会出现异常
     * 
     * @param key
     * @param value
     * @return
     */
    public long decr(String key, int value) {
        return client.decr(key, value);
    }

    /**
     * 获取异步结果
     * 
     * @param future
     * @return
     */
    private boolean getResult(OperationFuture<Boolean> future) {
        boolean result = false;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("future get error", e);
        }

        return result;
    }

}
