package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 用户组与角色的对应关系
 * 
 * @author ruban
 *
 */
public class AccountGroup {

    /** 主键 **/
    private int id;

    /** 用户组ID **/
    private int groupId;

    /** 角色ID **/
    private int roleId;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
