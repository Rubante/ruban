package com.ruban.monitor.memcached.database;

/**
 * memcached监控数据
 * 
 * @author yjwang
 *
 */
public interface MemcachedKey {

    /** 存储项前缀 **/
    public static final String PREFIX = "memcached:";

    /** 负载算法 **/
    public static final String LOCATOR = "locator";

    /** hash算法 **/
    public static final String HASH = "hash";

    /** 数据收集频率(stats) **/
    public static final String STATS_RATE = "statsRate";

    /** 数据收集频率 (stats setting) **/
    public static final String SETTING_RATE = "settingRate";

    /** 数据收集频率 (stats size) **/
    public static final String SIZE_RATE = "sizeRate";

    /** 数据收集状态 **/
    public static final String MONITOR_STATE = "monitorState";

    /** 反序列化类 **/
    public static final String SERIA_CLASS_NAME = "className";

    /** 服务器:ID **/
    public static final String ID = "id";

    /** 服务器:IP **/
    public static final String HOST = "host";

    /** 服务器:端口 **/
    public static final String PORT = "port";

    /** 服务器列表 **/
    public static final String SERVER_LIST = "servers";

    /** memcached监控stats **/
    public static final String STATS = "stats:";

    /** memcached监控stats size **/
    public static final String STATS_SIZE = "stats:size:";

    /** memcached监控stats setting **/
    public static final String STATS_SETTING = "stats:setting:";

    /** 数据列表 **/
    public static final String DATA_ID = "data:id:";

    /** KEY列表 **/
    public static final String DATA_KEY_ID = "data:key:";

    /** 数据ID与key 对照表 **/
    public static final String DATA_ID_KEY = "data:idkey:";

    /** CPU使用率 **/
    public static final String WARN_CPU = "warn:cpu:";

    /** 命中率 **/
    public static final String WARN_HIT = "warn:hit:";

    /** 报警配置:端口 **/
    public static final String VALUE = "value";

    /** 报警配置:频率 **/
    public static final String RATE = "rate";

    /** 报警配置:监控项 **/
    public static final String ITEM = "item";

}
