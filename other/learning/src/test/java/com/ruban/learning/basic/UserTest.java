package com.ruban.learning.basic;

import org.junit.Test;

public class UserTest {

    @Test
    public void reference() {
        UserTest test = new UserTest();
        User user = new User();
        test.change(user);
        System.out.println(user.getName());// 结果，原因
    }

    public void change(User user) {
        user.setName("hello world!");// A
        user = new User();
        user.setName("my gold!");// B
    }

}

class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}