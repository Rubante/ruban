package com.ruban.monitor.jvm.domain;

/**
 * JVM监控用数据
 * 
 * @author yjwang
 *
 */
public interface JVMKey {

    /** 存储项前缀 **/
    public static final String PREFIX = "jvm:";

    /** 服务器:ID **/
    public static final String ID = "id";

    /** 服务器:IP **/
    public static final String HOST = "host";

    /** 服务器:端口 **/
    public static final String PORT = "port";

    /** 服务器列表 **/
    public static final String SERVER_LIST = "servers";

    /** 数据收集频率 **/
    public static final String MONITOR_RATE = "rate";

    /** 数据收集状态 **/
    public static final String MONITOR_STATE = "state";

    /** 堆内存 **/
    public static final String MEM_HEAP = "heap:";

    /** 非堆内存 **/
    public static final String MEM_NON_HEAP = "nonHeap:";

    /** 堆内存:代码缓冲区 **/
    public static final String MEM_CODE_CACHE = "codeCache:";

    /** 堆内存:新生带 **/
    public static final String MEM_EDEN = "eden:";

    /** 堆内存：老年代 **/
    public static final String MEM_OLD_GEN = "oldGen:";

    /** 堆内存：永久带 **/
    public static final String MEM_PERM_GEN = "permGen:";

    /** 堆内存：新生代（交换区） **/
    public static final String MEM_SURVIVOR_SPACE = "survivorSpace:";

    /** 加载的类数量 **/
    public static final String LOADED_CLASS = "loadedclass:";
}
