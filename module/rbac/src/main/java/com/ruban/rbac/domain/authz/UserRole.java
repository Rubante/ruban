package com.ruban.rbac.domain.authz;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户与角色的关系
 * 
 * @author ruban
 *
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    private Long id;

    /** 用户ID **/
    private Long userId;

    /** 所属用户 **/
    private User user;

    /** 角色ID **/
    private Long roleId;

    /** 所设角色 **/
    private Role role;

    /** 是否是被委托的 **/
    private int delegated;

    /** 委托人 **/
    private Long delegateId;

    /** 委托时间 **/
    private Date delegateTime;

    /** 委托截至时间 **/
    private Date endTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getDelegated() {
        return delegated;
    }

    public void setDelegated(int delegated) {
        this.delegated = delegated;
    }

    public Long getDelegateId() {
        return delegateId;
    }

    public void setDelegateId(Long delegateId) {
        this.delegateId = delegateId;
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
