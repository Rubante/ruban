package com.ruban.fenxiang.service;

import java.util.List;

import com.ruban.fenxiang.domain.User;

public interface IUserService {

    public void addUser(User user);

    public User getUser(String id);
    
    public List<User> findAll();
}
