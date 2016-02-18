package com.ruban.rbac.domain.organization;

import java.util.Date;

/**
 * 公司内的职务设置
 * 
 * @author ruban
 *
 */
public class Title {

    /** 主键 **/
    private int id;

    /** 职务名称 **/
    private String name;

    /** 所属公司 **/
    private int companyId;

    /** 说明 **/
    private String memo;

    /** 更新时间 **/
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
