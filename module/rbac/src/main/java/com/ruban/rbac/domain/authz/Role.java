package com.ruban.rbac.domain.authz;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 组织机构中的角色，角色可以继承
 * 
 * @author ruban
 *
 */
public class Role extends PersistentObject {

    /** 角色名称 **/
    private String name;

    /** 角色类型 **/
    private String type;

    /** 状态 **/
    private int state;

    /** 是否可代理 **/
    private int delegated;

    /** 备注 **/
    private String memo;

    /** 所属公司 **/
    private Long companyId;

    /** 所属公司名称 **/
    private String companyName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDelegated() {
        return delegated;
    }

    public void setDelegated(int delegated) {
        this.delegated = delegated;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
