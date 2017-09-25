package com.ruban.monitor.jvm.bean;

import java.io.Serializable;
import java.lang.management.ClassLoadingMXBean;

/**
 * 加载过的信息
 * 
 * @author yjwang
 *
 */
public class LoadedClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    /**
     * 总共加载过的类
     */
    private long totalLoadedClassCount;

    /**
     * 目前加载的类
     * 
     */
    private int loadedClassCount;

    /**
     * 下载的类
     * 
     */
    private long unloadedClassCount;

    /**
     * verbose设置是否启用
     * 
     */
    private boolean verbose;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotalLoadedClassCount() {
        return totalLoadedClassCount;
    }

    public void setTotalLoadedClassCount(long totalLoadedClassCount) {
        this.totalLoadedClassCount = totalLoadedClassCount;
    }

    public int getLoadedClassCount() {
        return loadedClassCount;
    }

    public void setLoadedClassCount(int loadedClassCount) {
        this.loadedClassCount = loadedClassCount;
    }

    public long getUnloadedClassCount() {
        return unloadedClassCount;
    }

    public void setUnloadedClassCount(long unloadedClassCount) {
        this.unloadedClassCount = unloadedClassCount;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * 转化为一个新对象
     * 
     * @param classLoadingMXBean
     * @return
     */
    public static LoadedClassInfo wrap(ClassLoadingMXBean classLoadingMXBean) {

        LoadedClassInfo classInfo = new LoadedClassInfo();

        classInfo.setTotalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount());
        classInfo.setLoadedClassCount(classLoadingMXBean.getLoadedClassCount());
        classInfo.setUnloadedClassCount(classLoadingMXBean.getUnloadedClassCount());
        classInfo.setVerbose(classLoadingMXBean.isVerbose());

        return classInfo;

    }
}
