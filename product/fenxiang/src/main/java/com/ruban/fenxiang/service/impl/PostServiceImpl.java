package com.ruban.fenxiang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ruban.fenxiang.domain.Post;
import com.ruban.fenxiang.service.IPostService;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    MongoTemplate template;

    @Override
    public void add(Post post) {
        template.insert(post);
    }

}
