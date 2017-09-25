package com.ruban.monitor.jvm.bean;

import java.lang.management.MemoryUsage;

public class MemoryTool {

    public static final String G1 = "G1";

    public static final String CMS = "CMS";

    public static final String PS = "PS";

    // 串行垃圾回收器
    public static final String COLLACTOR_SERIAL = "-XX:+UseSerialGC";

    // 并行垃圾回收器
    public static final String COLLACTOR_PARALLEL = "-XX:+UseParallelGC";

    public static final String COLLACTOR_PARNEW = "-XX:+USeParNewGC";

    // 并发标记扫描垃圾回收器
    public static final String COLLACTOR_CMS = "-XX:+UseConcMarkSweepGC";

    // G1垃圾回收器
    public static final String COLLACTOR_G1 = "-XX:+UseG1GC";

    public static String getCodeCache() {
        return "java.lang:name=Code Cache,type=MemoryPool";
    }

    public static String getEdenSpace(String collector) {
        return "java.lang:name=" + collector + " Eden Space,type=MemoryPool";
    }

    public static String getOldGen(String collector) {
        return "java.lang:name=" + collector + " Old Gen,type=MemoryPool";
    }

    public static String getPermGen(String collector) {
        return "java.lang:name=" + collector + " Perm Gen,type=MemoryPool";
    }

    public static String getMetaspace() {
        return "java.lang:name=Metaspace,type=MemoryPool";
    }

    public static String getSurvivorSpace(String collector) {
        return "java.lang:name=" + collector + " Survivor Space,type=MemoryPool";
    }

    /**
     * 转化memoryUsage对象
     * 
     * @param memoryUsage
     * @return
     */
    public static MemoryBean convert(MemoryUsage memoryUsage, Long id) {
        MemoryBean memoryBean = new MemoryBean();

        memoryBean.setId(id);
        memoryBean.setInit(memoryUsage.getInit());
        memoryBean.setUsed(memoryUsage.getUsed());
        memoryBean.setMax(memoryUsage.getMax());
        memoryBean.setCommitted(memoryUsage.getCommitted());

        return memoryBean;
    }

}
