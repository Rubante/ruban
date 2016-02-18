package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 该表代表一个组，组内有一定的用户
 * 
 * @author ruban
 *
 */
public class Group {

    /** 主键 **/
    private int id;

    /** 组名 **/
    private String name;

    /** 设置时间 **/
    private Date setupTime;

    /** 状态 **/
    private int status;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(Date setupTime) {
        this.setupTime = setupTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
