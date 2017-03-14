package com.ruban.rbac.domain.organization;

import java.util.Date;

/**
 * 组织内的各个实体的员工信息
 * 
 * @author ruban
 *
 */
public class Employee {

    /** 主键 **/
    private int id;

    /** 员工编码 **/
    private String code;

    /** 出生日期 **/
    private String birthday;

    /** 邮箱 **/
    private String email;

    /** 性别 **/
    private int sex;

    /** 身份证 **/
    private String cardid;

    /** 住址 **/
    private String address;

    /** 入职时间 **/
    private String entryDate;

    /** 离职时间 **/
    private String departureDate;

    /** 薪资 **/
    private String salary;

    /** 职务 **/
    private int titleId;

    /** 所属公司 **/
    private int companyId;

    /** 状态 **/
    private String status;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
