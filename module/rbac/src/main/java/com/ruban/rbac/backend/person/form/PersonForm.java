package com.ruban.rbac.backend.person.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.ruban.rbac.backend.BackendForm;

@Validated
public class PersonForm extends BackendForm implements Serializable {

    private static final long serialVersionUID = -8068180251093563415L;

    /** 实体ID **/
    private Long id;

    /** 编码 **/
    private String code;

    /** 姓名 **/
    @NotNull(message = "{person.name.notnull}")
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

    /** 是否删除照片 **/
    private String delPhoto;

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

    /** 职务 **/
    private Long titleId;

    /** 状态 **/
    private String status;

    /** 备注 **/
    private String memo;

    /** 所属部门 **/
    private Long departmentId;

    /** 所属公司 **/
    @NotNull(message = "{person.company.notnull}")
    private Long companyId;

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

    public String getDelPhoto() {
        return delPhoto;
    }

    public void setDelPhoto(String delPhoto) {
        this.delPhoto = delPhoto;
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

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getHoldLock() {
        return holdLock;
    }

    public void setHoldLock(String holdLock) {
        this.holdLock = holdLock;
    }

}
