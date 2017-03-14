package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 权限所有拥有的资源下，有哪些数据可供其拥有<br />
 * 主要对数据获取权限进行列级别的控制.<br/>
 * 对列数据的控制，设计及实现都有很多问题
 * 
 * @author tdr
 *
 */
public class PermissionResourceField {

    /** 主键 **/
    private int id;

    /** 资源ID **/
    private int resourcesId;

    /** 资源属性 **/
    private int resourcesDataId;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(int resourcesId) {
        this.resourcesId = resourcesId;
    }

    public int getResourcesDataId() {
        return resourcesDataId;
    }

    public void setResourcesDataId(int resourcesDataId) {
        this.resourcesDataId = resourcesDataId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
