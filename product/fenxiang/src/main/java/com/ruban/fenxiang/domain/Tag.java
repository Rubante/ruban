package com.ruban.fenxiang.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * 标签
 * 
 * @author yjwang
 *
 */
public class Tag {

    @Id
    private String id;

    /**
     * tag名称
     * 
     */
    private String name;

    /**
     * 创建时间
     * 
     */
    private Date createDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
