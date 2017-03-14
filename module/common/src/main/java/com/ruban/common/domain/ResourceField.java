package com.ruban.common.domain;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 系统资源下有那些可操作的数据
 * 
 * @author ruban
 *
 */
public class ResourceField extends PersistentObject {

    /** 字段值 **/
    private String code;

    /** 字段名 **/
    private String name;

    /** 是否可授权 **/
    private int flag;

    /** 备注 **/
    private String memo;

    /** 所属资源 **/
    private Long resourceId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

}
