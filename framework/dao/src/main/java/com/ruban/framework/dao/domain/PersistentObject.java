package com.ruban.framework.dao.domain;

import java.util.Date;

/**
 * 业务对象接口
 * 
 * 确保每个业务对象都有UPDATE_LOCK字段，用于乐观锁的控制
 * 
 * @author ruban
 *
 */
public class PersistentObject {

    /** 主键 **/
    private Long id;
    /** 添加时间 **/
    private Date addTime;
    /** 添加者ID **/
    private Long addUserId;
    /** 修改时间 **/
    private Date modTime;
    /** 修改者 **/
    private Long modUserId;
    /** 更新锁 **/
    private String updateLock;
    /** 持有锁 **/
    private String holdLock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
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

    public String getUpdateLock() {
        return updateLock;
    }

    public void setUpdateLock(String updateLock) {
        this.updateLock = updateLock;
    }

    public String getHoldLock() {
        return holdLock;
    }

    public void setHoldLock(String holdLock) {
        this.holdLock = holdLock;
    }

}
