package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 系统可控制的资源，访问路径，按钮等
 * 
 * @author ruban
 *
 */
public class Resources {

    /** 主键 **/
    private int id;

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
    private int status;

    /** 修改时间 **/
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
