package com.ruban.fenxiang.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * 
 * 分享的文章：<br />
 * 链接地址<br />
 * 分享者<br />
 * 分享时间<br />
 * 摘要<br />
 * 状态<br />
 * 浏览者<br />
 * 支持者<br />
 * 反对者<br />
 * 所属标签<br />
 * 
 * @author yjwang
 *
 */
public class Article {

    @Id
    private String id;

    /***
     * 链接地址
     */
    private String url;

    /**
     * 分享者
     * 
     */
    private User createUser;

    /**
     * 分享时间
     * 
     */
    private Date createTime;

    /**
     * 摘要
     * 
     */
    private String memo;

    /**
     * 状态
     * 
     */
    private String status;

    /**
     * 浏览者
     * 
     */
    private String browers;

    /**
     * 支持者
     * 
     */
    private String supports;

    /**
     * 反对者
     * 
     */
    private String antis;

    /**
     * 所属标签
     * 
     */
    private Tag[] tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrowers() {
        return browers;
    }

    public void setBrowers(String browers) {
        this.browers = browers;
    }

    public String getSupports() {
        return supports;
    }

    public void setSupports(String supports) {
        this.supports = supports;
    }

    public String getAntis() {
        return antis;
    }

    public void setAntis(String antis) {
        this.antis = antis;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

}
