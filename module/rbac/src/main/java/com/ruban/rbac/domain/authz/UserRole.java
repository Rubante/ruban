package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 用户与角色的关系
 * 
 * @author ruban
 *
 */
public class UserRole {

    /** 主键 **/
    private int id;

    /** 用户ID **/
    private int userId;

    /** 角色ID **/
    private int roleId;

    /** 是否可委托 **/
    private int delegated;

    /** 委托人 **/
    private int delegatedId;

    /** 委托时间 **/
    private Date delegateTime;

    /** 委托截至时间 **/
    private Date endTime;

    /** 修改时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getDelegated() {
        return delegated;
    }

    public void setDelegated(int delegated) {
        this.delegated = delegated;
    }

    public int getDelegatedId() {
        return delegatedId;
    }

    public void setDelegatedId(int delegatedId) {
        this.delegatedId = delegatedId;
    }

    public Date getDelegateTime() {
        return delegateTime;
    }

    public void setDelegateTime(Date delegateTime) {
        this.delegateTime = delegateTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
