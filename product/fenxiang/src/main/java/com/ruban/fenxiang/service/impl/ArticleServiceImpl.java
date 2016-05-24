package com.ruban.fenxiang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ruban.fenxiang.domain.Article;
import com.ruban.fenxiang.service.IArticleService;

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private MongoTemplate template;

    @Override
    public void add(Article article) {
        template.insert(article);
    }

    @Override
    public List<Article> findList(String queryCondition) {
        return template.find(new Query().with(new Sort(Direction.DESC, "createTime")), Article.class);
    }

}
