package com.ruban.monitor.server.bean;

import java.io.Serializable;

/**
 * 用户组关系
 * 
 * @author yjwang
 *
 */
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户id **/
    private Long userId;

    /** 组id **/
    private Long groupId;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
