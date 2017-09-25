package com.ruban.rbac.domain.authz;

import java.io.Serializable;
import java.util.Date;

/**
 * 父角色所拥有的子角色，这样需要查找多次才能把整个角色数建立起来
 * 
 * @author ruban
 *
 */
public class RoleExt  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** 注解ID **/
    private Long id;

    /** 父角色 **/
    private Long roleId;

    /** 子角色 **/
    private Long childId;

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

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
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
