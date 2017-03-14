package com.ruban.rbac.domain.organization;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 该表代表组织内部的部门，科室，委员会等行政设立的<br />
 * 部门经理（负责人）是非必须的，可以通过职务来设定部门负责人，此处仅为展示之用<br />
 * 
 * @author ruban
 *
 */
public class Department extends PersistentObject {

    /** 部门编码 **/
    private String code;

    /** 部门名称 **/
    private String name;

    /** 部门简称 **/
    private String simpleName;

    /** 成立时间 **/
    private String setupTime;

    /** 状态 **/
    private int state;

    /** 备注 **/
    private String memo;

    /** 排序序号 **/
    private int orderCode;

    /** 部门经理（负责人） **/
    private int personId;

    /** 部门经理（负责人） **/
    private String personName;

    /** 父节点 **/
    private Long departmentId;

    /** 父节点名称 **/
    private String departmentName;

    /** 所属组织机构 **/
    private Long companyId;

    /** 所属组织机构名称 **/
    private String companyName;

    /** 子节点数量 **/
    private int hasChildren;

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

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(int hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
