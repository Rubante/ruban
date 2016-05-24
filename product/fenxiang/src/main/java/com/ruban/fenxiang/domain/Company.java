package com.ruban.fenxiang.domain;

import org.springframework.data.annotation.Id;

/**
 * 用户所属公司
 * 
 * @author yjwang
 *
 */
public class Company {

    @Id
    private String id;

    /**
     * 公司名称
     * 
     */
    private String name;

    /**
     * 公司地址
     * 
     */
    private String address;

    /**
     * 所属行业
     * 
     */
    private String type;

    /**
     * 公司简介
     * 
     */
    private String decription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

}
