package com.ruban.rbac.domain.organization;

import java.util.Date;

/**
 * 该表代表组织内部的部门，科室，委员会等行政设立的
 * 
 * @author ruban
 *
 */
public class Department {

    /** 主键 **/
    private int id;

    /** 公司 **/
    private int companyId;

    /** 部门名称 **/
    private String name;

    /** 成立时间 **/
    private Date setupTime;

    /** 状态 **/
    private int status;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(Date setupTime) {
        this.setupTime = setupTime;
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
