package com.ruban.rbac.domain.organization;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 组织机构内各个实体的人员信息
 * 
 * @author ruban
 *
 */
public class Person extends PersistentObject {

    /** 编码 **/
    private String code;

    /** 姓名 **/
    private String name;

    /** 出生日期 **/
    private String birthday;

    /** 邮箱 **/
    private String email;

    /** 性别 **/
    private int sex;

    /** 身份证 **/
    private String cardid;

    /** 照片 **/
    private String photo;

    /** 住址 **/
    private String address;

    /** 入职时间 **/
    private String entryDate;

    /** 离职时间 **/
    private String departureDate;

    /** 薪资 **/
    private String salary;

    /** 岗位 **/
    private Long jobId;

    /** 岗位名称 **/
    private String jobName;

    /** 职务 **/
    private Long titleId;

    /** 职务名称 **/
    private String titleName;

    /** 备注 **/
    private String memo;

    /** 排序编码(部门内) **/
    private int orderCode;

    /** 所属部门 **/
    private Long departmentId;

    /** 部门名称 **/
    private String departmentName;

    /** 所属公司 **/
    private Long companyId;

    /** 所属公司组织 **/
    private String companyName;

    /** 状态 **/
    private int state;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
