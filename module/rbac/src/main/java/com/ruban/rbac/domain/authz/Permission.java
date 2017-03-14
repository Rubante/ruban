package com.ruban.rbac.domain.authz;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 系统中的权限
 * 
 * @author ruban
 *
 */
public class Permission extends PersistentObject {

    /** 操作方式 **/
    private String type;

    /** 权限名 **/
    private String name;

    /** 备注 **/
    private String memo;

    /** 状态 **/
    private int state;

    /** 所属公司 **/
    private Long companyId;

    /** 所属公司名称 **/
    private String companyName;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
