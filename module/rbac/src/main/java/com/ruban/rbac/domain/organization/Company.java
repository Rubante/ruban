package com.ruban.rbac.domain.organization;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 该表代表各种形式的组织：集团，公司，办事处等。<br />
 * 可能存在多条数据，在规模庞大的集团中，<br />
 * 代表集团，分公司，各办事处等 在单一组织中，该表中只需存储一条数据即可
 * 
 * @author ruban
 *
 */
public class Company {

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
