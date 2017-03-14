package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 权限对应的资源
 * 
 * @author ruban
 *
 */
public class PermissionResources {

    /** 主键 **/
    private int id;

    /** 权限ID **/
    private int permissionId;

    /** 资源ID **/
    private int resourcesId;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(int resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
