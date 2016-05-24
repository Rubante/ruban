package com.ruban.fenxiang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ruban.fenxiang.domain.User;
import com.ruban.fenxiang.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private MongoTemplate template;

    @Override
    public void addUser(User user) {
        template.save(user, "USER");

    }

    @Override
    public User getUser(String id) {
        return template.findOne(new Query(Criteria.where("_id").is(id)), User.class, "USER");
    }

    @Override
    public List<User> findAll() {

        return template.findAll(User.class, "USER");
    }
}
