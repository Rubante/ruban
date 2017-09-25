package com.ruban.monitor.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.ruban.monitor.common.ConfigHolder;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.dao.serialize.MyRedisSerialize;

import redis.clients.jedis.JedisPoolConfig;

@Component("redisDao")
@Lazy
public class RedisDaoImpl<T> implements CommonDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(RedisDaoImpl.class);

    protected RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();

    private JedisConnectionFactory connectionFactory;

    /** 是否准备好 **/
    private boolean ready = false;

    @Override
    public void refresh(Properties config) {

        // 如果已经存在，则销毁
        if (connectionFactory != null) {
            connectionFactory.destroy();
        }

        connectionFactory = new JedisConnectionFactory();

        String host = config.getProperty(MonitorConstant.REDIS_HOST);
        String port = config.getProperty(MonitorConstant.REDIS_PORT);
        String password = config.getProperty(MonitorConstant.REDIS_PASSWORD);
        String database = config.getProperty(MonitorConstant.REDIS_DATABASE);

        connectionFactory.setHostName(host);
        connectionFactory.setPort(Integer.parseInt(port));
        connectionFactory.setPassword(password);
        connectionFactory.setDatabase(Integer.parseInt(database));
        connectionFactory.setPoolConfig(new JedisPoolConfig());

        connectionFactory.afterPropertiesSet();

        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new MyRedisSerialize<>());
        redisTemplate.afterPropertiesSet();

        logger.info("redis config set! ip:" + host + ", port:" + port + ", password:" + password + ", database:" + database);

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
        connectionFactory.destroy();
    }

    /**
     * 通过key获取 <br>
     * ------------------------------<br>
     * 
     * @param key
     * @return
     */
    public T get(String key) {
        key = wrapkey(key);
        return redisTemplate.opsForValue().get(key);
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
        key = wrapkey(key);

        return redisTemplate.boundValueOps(key).setIfAbsent(value);
    }

    /**
     * 删除 <br>
     * ------------------------------<br>
     * 
     * @param key
     */
    @Override
    public void delete(String key) {
        key = wrapkey(key);

        List<String> list = new ArrayList<String>();
        list.add(key);
        delete(list);
    }

    /**
     * 删除多个 <br>
     * ------------------------------<br>
     * 
     * @param keys
     */
    @Override
    public void delete(List<String> keys) {
        redisTemplate.delete(keys);
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
        key = wrapkey(key);

        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public void addToList(String key, T value) {
        key = wrapkey(key);

        redisTemplate.boundListOps(key).rightPush(value);
    }

    @Override
    public T getFromList(String key, int index) {
        key = wrapkey(key);

        return redisTemplate.boundListOps(key).index(index);
    }

    @Override
    public List<T> getList(String key, int start, int end) {
        key = wrapkey(key);

        return redisTemplate.boundListOps(key).range(start, end);
    }

    @Override
    public List<T> getAllList(String key) {
        key = wrapkey(key);

        return redisTemplate.boundListOps(key).range(0, -1);
    }

    @Override
    public long sizeOfList(String key) {
        key = wrapkey(key);

        return redisTemplate.boundListOps(key).size();
    }

    @Override
    public void remOfList(String key, int start, int end) {
        key = wrapkey(key);

        redisTemplate.boundListOps(key).trim(start, end);
    }

    @Override
    public void removeFromList(String key, T value) {
        redisTemplate.boundListOps(key).remove(1, value);
    }

    @Override
    public void putToZSet(String key, long vKey, T value) {
        key = wrapkey(key);

        BoundZSetOperations<String, T> operation = redisTemplate.boundZSetOps(key);
        // 清除设置新值
        operation.removeRangeByScore(vKey, vKey);
        operation.add(value, vKey);
    }

    @Override
    public Map<Long, T> getFromZSet(String key, long start, long end) {
        key = wrapkey(key);

        Set<ZSetOperations.TypedTuple<T>> sets = redisTemplate.boundZSetOps(key).rangeByScoreWithScores(start, end);

        Iterator<TypedTuple<T>> iterator = sets.iterator();

        Map<Long, T> map = new TreeMap<>();

        while (iterator.hasNext()) {
            TypedTuple<T> typedTuple = iterator.next();
            map.put(typedTuple.getScore().longValue(), typedTuple.getValue());
        }

        return map;
    }

    @Override
    public T getFromZSet(String key, long start) {
        key = wrapkey(key);

        Set<ZSetOperations.TypedTuple<T>> sets = redisTemplate.boundZSetOps(key).rangeByScoreWithScores(start, start);

        Iterator<TypedTuple<T>> iterator = sets.iterator();

        while (iterator.hasNext()) {
            TypedTuple<T> typedTuple = iterator.next();
            return typedTuple.getValue();
        }

        return null;
    }

    @Override
    public T getFloorFromZSet(String key, long skey) {
        key = wrapkey(key);

        BoundZSetOperations<String, T> operation = redisTemplate.boundZSetOps(key);
        Set<T> sets = operation.range(operation.size() - 1, operation.size());

        Iterator<T> iterator = sets.iterator();

        while (iterator.hasNext()) {
            T value = iterator.next();
            return value;
        }

        return null;
    }

    @Override
    public boolean containsInZSet(String key, T value) {
        key = wrapkey(key);

        BoundZSetOperations<String, T> operation = redisTemplate.boundZSetOps(key);
        Long index = operation.rank(value);

        return index != 0;
    }

    @Override
    public void flushZSet(String key) {
        key = wrapkey(key);

        BoundZSetOperations<String, T> operation = redisTemplate.boundZSetOps(key);
        operation.getOperations().delete(key);
    }

    @Override
    public T removeFromZSet(String key, long skey) {
        key = wrapkey(key);

        redisTemplate.boundZSetOps(key).removeRangeByScore(skey, skey);

        return null;
    }

    @Override
    public <V> void putToHash(String key, V vKey, T value) {
        key = wrapkey(key);

        redisTemplate.boundHashOps(key).put(vKey, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> T getFromHash(String key, V vKey) {
        key = wrapkey(key);

        return (T) redisTemplate.boundHashOps(key).get(vKey);
    }

    @Override
    public <V> void removeFromHash(String key, V vKey) {
        key = wrapkey(key);

        redisTemplate.boundHashOps(key).delete(vKey);
    }

    /**
     * 
     * 
     * @param key
     * @return
     */
    private String wrapkey(String key) {
        return ConfigHolder.get(MonitorConstant.REDIS_PREFIX) + key;
    }
}
