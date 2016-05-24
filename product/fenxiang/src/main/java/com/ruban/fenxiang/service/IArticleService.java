package com.ruban.fenxiang.service;

import java.util.List;

import com.ruban.fenxiang.domain.Article;

/**
 * 文章
 * 
 * @author yjwang
 *
 */
public interface IArticleService {

    /**
     * 添加文章
     * 
     * @param article
     */
    public void add(Article article);

    /**
     * 查询文章
     * 
     * @param queryCondition
     * @return
     */
    public List<Article> findList(String queryCondition);
}
