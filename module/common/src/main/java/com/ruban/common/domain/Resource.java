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

    /** 资源编码 **/
    private String code;

    /** 全路径 **/
    private String path = "";

    /** 资源类型 **/
    private int type;

    /** 访问路径 **/
    private String link;

    /** 图标 **/
    private String icon;

    /** 是否父节点 **/
    private Long parentId = 0L;

    /** 父节点名称 **/
    private String parentName;

    /** 是否显示 **/
    private int displayFlag = 0;

    /** 状态 **/
    private int state;

    /** 排序字段* */
    private String orderCode;

    /** 层次等级 **/
    private int level;

    /** 所属公司 **/
    private Long companyId;

    /** 备注 **/
    private String memo;

    /** 是否有字节点 **/
    private int childrenNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(int displayFlag) {
        this.displayFlag = displayFlag;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(int childrenNum) {
        this.childrenNum = childrenNum;
    }

}
