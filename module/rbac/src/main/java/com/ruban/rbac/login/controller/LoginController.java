package com.ruban.rbac.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ruban.rbac.login.form.LoginForm;

@Controller
@RequestMapping(value = "/rbac")
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("form") LoginForm form) {
        System.out.println(form.getUserno());
        return "main";
    }
}
