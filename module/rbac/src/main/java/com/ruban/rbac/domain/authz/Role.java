package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 组织机构中的角色，角色可以继承
 * 
 * @author tdr
 *
 */
public class Role {

    /** 主键 **/
    private int id;

    /** 角色名称 **/
    private String name;

    /** 角色类型 **/
    private String type;

    /** 状态 **/
    private int status;

    /** 备注 **/
    private String memo;

    /** 修改时间 **/
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
