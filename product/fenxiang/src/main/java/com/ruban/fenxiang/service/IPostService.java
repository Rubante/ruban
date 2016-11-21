package com.ruban.fenxiang.service;

import com.ruban.fenxiang.domain.Post;

/**
 * 评论操作
 * 
 * @author yjwang
 *
 */
public interface IPostService {

    /**
     * 添加评论
     * 
     * @param post
     */
    public void add(Post post);
}
