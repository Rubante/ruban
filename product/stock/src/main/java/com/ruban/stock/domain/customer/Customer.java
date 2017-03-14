package com.ruban.stock.domain.customer;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 客户。<br />
 * 
 * @author ruban
 *
 */
public class Customer {

    /** 主键 **/
    private int id;
    @NotNull
    /** 编码 **/
    @NotEmpty
    private String code;
    /** 名称 **/
    private String name;
    /** 地址 **/
    private String address;
    /** 邮编 **/
    private String postCode;
    /** 抬头 **/
    private String title;
    /** 电话 **/
    private String tel;
    /** 邮箱 **/
    private String email;
    /** 母公司 **/
    private int parentId;
    /** 组织类型 **/
    private int type;
    /** 备注 **/
    private String memo;
    /** 更新时间 **/
    private Date updateTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
