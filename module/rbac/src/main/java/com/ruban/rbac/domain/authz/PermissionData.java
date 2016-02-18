package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 系统资源下有那些可操作的数据
 * 
 * @author ruban
 *
 */
public class PermissionData {

    /** 主键 **/
    private int id;

    /** 属性名 **/
    private String name;

    /** 字段名 **/
    private String fieldName;

    /** 是否可授权 **/
    private int flag;

    /** 所属资源 **/
    private int resourcesId;

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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
