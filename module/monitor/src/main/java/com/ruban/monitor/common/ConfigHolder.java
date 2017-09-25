package com.ruban.monitor.common;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ruban.monitor.memcached.MemcachedMonitor;

/**
 * 存储当前的配置文件信息
 * 
 * @author yjwang
 *
 */
public class ConfigHolder {

    private static final Logger logger = LoggerFactory.getLogger(ConfigHolder.class);

    private static Properties properties;

    /** 存储调度任务 **/
    private static Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>();

    /** memcache监控器 **/
    private static MemcachedMonitor monitor;

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        ConfigHolder.properties = properties;
    }

    /***
     * 返回value
     * 
     * @param key
     * @return
     */
    public static String get(String key) {
        if (properties == null) {
            try {
                properties = PropertiesLoaderUtils.loadAllProperties(MonitorConstant.CONFIG);
            } catch (IOException ex) {
                logger.error("load properties error!", ex);
            }
        }
        return properties.getProperty(key);
    }

    /**
     * 存储调度器
     * 
     * @param key
     * @param future
     */
    public static void putFutrue(String key, ScheduledFuture<?> future) {
        futureMap.put(key, future);
    }

    /**
     * 获取对应的调度器
     * 
     * @param key
     * @return
     */
    public static ScheduledFuture<?> getFuture(String key) {
        return futureMap.get(key);
    }

    public static MemcachedMonitor getMonitor() {
        return monitor;
    }

    public static void setMonitor(MemcachedMonitor monitor) {
        ConfigHolder.monitor = monitor;
    }

}
