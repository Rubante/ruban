package com.ruban.monitor.common;

/**
 * 各种常量
 * 
 * @author yjwang
 *
 */
public interface MonitorConstant {

    /** 配置文件名称 **/
    public static final String CONFIG = "config.properties";

    /** 用户登录时间 **/
    public static final String LOGIN_TIME = "loginTime";

    /** 地址 **/
    public static final String HOST = "host";

    /** 端口 **/
    public static final String PORT = "port";

    /** 密码 **/
    public static final String PASSWORD = "password";

    /** 索引值 **/
    public static final String DATABASE = "database";

    /** 数据库前缀 **/
    public static final String PREFIX = "prefix";

    /** 数据库 **/
    public static final String DB = "db.";

    /** 数据库存储种类 **/
    public static final String SELECT = "select";

    /** 数据路径 **/
    public static final String FILE_PATH = "filePath";

    /** 数据库存储种类 **/
    public static final String DB_SELECT = DB + SELECT;

    /** 数据库存储路径 **/
    public static final String DB_FILE_PATH = DB + FILE_PATH;

    /** 监控用redis 地址 **/
    public static final String REDIS_DOT = "redis.";

    /** 监控用redis 地址 **/
    public static final String REDIS_HOST = REDIS_DOT + HOST;

    /** 监控用redis 端口 **/
    public static final String REDIS_PORT = REDIS_DOT + PORT;

    /** 监控用redis 密码 **/
    public static final String REDIS_PASSWORD = REDIS_DOT + PASSWORD;

    /** redis 索引值 **/
    public static final String REDIS_DATABASE = REDIS_DOT + DATABASE;

    /** 监控用redis 数据库前缀 **/
    public static final String REDIS_PREFIX = REDIS_DOT + PREFIX;

    /** 选择mapdb **/
    public static final String SELECT_MAPDB = "1";

    /** 选择redis **/
    public static final String SELECT_REDIS = "2";

    /** 通用错误码 **/
    public static final int MSG_ERROR_CODE = 1;

    /** redis bean dao **/
    public static final String REDIS_DAO = "redisDao";

    /** mapdb bean dao **/
    public static final String MAPDB_DAO = "mapDBDao";

    /** 登录信息 **/
    public static final String LOGINED = "loginedUser";

    /** 信息key值 **/
    public static final String MSG = "msg";
}
