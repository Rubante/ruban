package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 角色互斥 :<br />
 * 表明职责分离的目的设立该表，互斥角色不能授予同一个角色使用者
 * 
 * @author ruban
 *
 */
public class RoleMutex {

    /** 主键 **/
    private int id;

    /** 角色ID **/
    private int roleId;

    /** 反角色 **/
    private int mutexId;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMutexId() {
        return mutexId;
    }

    public void setMutexId(int mutexId) {
        this.mutexId = mutexId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
