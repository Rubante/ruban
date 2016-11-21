package com.ruban.fenxiang.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * 针对分享的文章发起的评论
 * 
 * @author yjwang
 *
 */
public class Post {

    @Id
    private String id;

    /**
     * 文章ID
     * 
     */
    private String articleId;

    /**
     * 星级
     * 
     */
    private String start;

    /**
     * 评论创建者
     * 
     */
    private User createUser;

    /**
     * 评论创建时间
     * 
     */
    private Date createDate;

    /**
     * 评论
     * 
     */
    private String comments;

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
     * 状态
     * 
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
