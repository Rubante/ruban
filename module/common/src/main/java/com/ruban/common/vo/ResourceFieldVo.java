package com.ruban.common.vo;

import java.io.Serializable;

public class ResourceFieldVo implements Serializable {

    private static final long serialVersionUID = -8068180251093563415L;

    /** 实体ID **/
    private Long id;

    /** 属性值 **/
    private String code;

    /** 是否可授权 **/
    private int flag;

    /** 备注 **/
    private String memo;

    /** 属性名称 **/
    private String name;

    /** 资源包ID **/
    private Long resourceId;

    /** 持有的锁 **/
    private String holdLock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getHoldLock() {
        return holdLock;
    }

    public void setHoldLock(String holdLock) {
        this.holdLock = holdLock;
    }

}
