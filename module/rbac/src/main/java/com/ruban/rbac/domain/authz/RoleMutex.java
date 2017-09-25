package com.ruban.rbac.domain.authz;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色互斥 :<br />
 * 表明职责分离的目的设立该表，互斥角色不能授予同一个角色使用者
 * 
 * @author ruban
 *
 */
public class RoleMutex implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    private Long id;

    /** 角色ID **/
    private Long roleId;

    /** 反角色 **/
    private Long mutexId;

    /** 修改时间 **/
    private Date modTime;

    /** 修改者 **/
    private Long modUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMutexId() {
        return mutexId;
    }

    public void setMutexId(Long mutexId) {
        this.mutexId = mutexId;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Long getModUserId() {
        return modUserId;
    }

    public void setModUserId(Long modUserId) {
        this.modUserId = modUserId;
    }

}
