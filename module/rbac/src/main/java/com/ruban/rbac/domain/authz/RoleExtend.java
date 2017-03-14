package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 父角色所拥有的子角色，这样需要查找多次才能把整个角色数建立起来
 * 
 * @author ruban
 *
 */
public class RoleExt {

    /** 注解ID **/
    private int id;

    /** 父角色 **/
    private int parentId;

    /** 子角色 **/
    private int childId;

    /** 修改时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
