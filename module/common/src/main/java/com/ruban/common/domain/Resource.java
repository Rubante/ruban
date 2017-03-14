package com.ruban.common.domain;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 系统可控制的资源，访问路径，按钮等
 * 
 * @author ruban
 *
 */
public class Resource extends PersistentObject {

    /** 资源名称 **/
    private String name;

    /** 资源类型 **/
    private int type;

    /** 访问路径 **/
    private String link;

    /** 图标 **/
    private String icon;

    /** 是否父节点 **/
    private int parentId;

    /** 是否可授权 **/
    private int flag;

    /** 状态 **/
    private int state;

    /** 所属公司 **/
    private Long companyId;

    /** 备注 **/
    private String memo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
