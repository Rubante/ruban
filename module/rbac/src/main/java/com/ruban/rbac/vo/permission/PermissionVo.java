package com.ruban.rbac.vo.permission;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

@Validated
public class PermissionVo implements Serializable {

    private static final long serialVersionUID = -8068180251093563415L;

    /** 主键 **/
    private Long id;

    /** 权限类型 **/
    private String type;

    /** 权限名 **/
    private String name;

    /** 说明 **/
    private String memo;

    /** 状态 **/
    private int state;

    /** 所属组织机构 **/
    private Long companyId;

    /** 持有的锁 **/
    private String holdLock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getHoldLock() {
        return holdLock;
    }

    public void setHoldLock(String holdLock) {
        this.holdLock = holdLock;
    }

}
