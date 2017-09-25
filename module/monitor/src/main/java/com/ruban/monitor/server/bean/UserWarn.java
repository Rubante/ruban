package com.ruban.monitor.server.bean;

import java.io.Serializable;

/**
 * 用户报警项管理
 * 
 * @author yjwang
 *
 */
public class UserWarn implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** memcached报警项 **/

    /** CPU使用率 **/
    private String memCpu;

    /** 命中率 **/
    private String memHit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemCpu() {
        return memCpu;
    }

    public void setMemCpu(String memCpu) {
        this.memCpu = memCpu;
    }

    public String getMemHit() {
        return memHit;
    }

    public void setMemHit(String memHit) {
        this.memHit = memHit;
    }

}
