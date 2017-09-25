package com.ruban.monitor.jvm.bean;

import java.io.Serializable;

/**
 * 内存数据对象
 * 
 * @author yjwang
 *
 */
public class MemoryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private long init;
    private long used;
    private long committed;
    private long max;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInit() {
        return init;
    }

    public void setInit(long init) {
        this.init = init;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getCommitted() {
        return committed;
    }

    public void setCommitted(long committed) {
        this.committed = committed;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

}
