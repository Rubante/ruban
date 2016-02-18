package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 角色所拥有的权限
 * 
 * @author ruban
 *
 */
public class RolePermission {

    /** 主键ID **/
    private int id;

    /** 角色ID **/
    private int roleId;

    /** 权限ID **/
    private int permissionId;

    /** 修改时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
