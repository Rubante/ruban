package com.ruban.fenxiang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruban.fenxiang.domain.User;
import com.ruban.fenxiang.service.IUserService;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/test/user")
    @ResponseBody
    public String test(@RequestBody User user) {

        System.out.println(user.getName());
        
        if("yjwang".equals(user.getName())){
            return "error";
        }
        
        userService.addUser(user);
        
        List<User> result = userService.findAll();

        System.out.println(result.size());
        
        return "{\"a\":\"a\"}";
    }
}
