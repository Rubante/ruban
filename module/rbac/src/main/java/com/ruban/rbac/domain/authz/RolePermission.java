package com.ruban.rbac.domain.authz;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色所拥有的权限
 * 
 * @author ruban
 *
 */
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    private Long id;

    /** 角色ID **/
    private Long roleId;

    /** 资源ID **/
    private Long resourceId;

    /** 是否可以授权给别人 **/
    private int flag;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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
